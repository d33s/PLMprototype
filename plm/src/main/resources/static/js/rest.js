var jsonOnline;

function addToDatabase(addWhat) {

	var oElements = {};
		$('form [name]').each(function(){
			oElements[this.name] = this.value;
		});
		
	var stringified = JSON.stringify(oElements);
	console.log(stringified);
	
	var sendEqUrl;
	
	switch (addWhat) {
    case 'customer':
        sendEqUrl = "http://localhost:8080/customers";
        break;
	case 'order':
        sendEqUrl = "http://localhost:8080/orders";
        break;
	case 'assembly':
        sendEqUrl = "http://localhost:8080/assemblies";
        break;
	case 'part':
        sendEqUrl = "http://localhost:8080/parts";
        break;
	}

    var sendEquationRequest = new XMLHttpRequest();

    sendEquationRequest.open('POST', sendEqUrl, true);
    sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    sendEquationRequest.onload = function () {
        if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
            // Success!
            var result = sendEquationRequest.responseText;
            document.getElementById("response").innerHTML = result;
        } else {
			// We reached our target server, but it returned an error
            alert("Could not download data!")
            console.log("Could not download data!");
        }
    };
    sendEquationRequest.send(stringified);
}

function addSearchBar(){
    var divSearchBar = document.createElement('div');
    divSearchBar.width = "100%";

    var searchTile = document.createElement('div');
    searchTile.className = 'searchButton';
    searchTile.innerHTML = "<i class = \"icon-search\"></i> Search for:";
    searchTile.addEventListener("click", function(){
        var idToSearchFor = document.getElementById('contentFrame').contentWindow.document.getElementById('entID').value;
        if (document.getElementById('optionSiteName').innerHTML === "#Delete") {
            showDBrecords(idToSearchFor, function(){
                addSearchBar()
                addDelOption();
            });
        }
        else if (document.getElementById('optionSiteName').innerHTML === "#Modify") {
            showDBrecords(idToSearchFor, function(){
                addSearchBar()
                addModifyOption();
            });
        }
        else{
            showDBrecords(idToSearchFor);
        }
    });
    divSearchBar.appendChild(searchTile);

    var searchWhat = document.createElement('div');
    searchWhat.className = "frameInput";
    searchWhat.innerHTML = "<input id=\"entID\" style=\"background-color: #8eb9ff;\" type=\"text\" name=\"toSearchID\" value=\"Enter ID\" onfocus=\"if (this.value=='Enter ID') this.value='';\">";
    divSearchBar.appendChild(searchWhat);

    var frameContent = document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull');
    frameContent.insertBefore(divSearchBar, frameContent.childNodes[0]);
    //document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull').appendChild(divSearchBar);
}

function showTable(jsonRCVD, callback){

    var obj = JSON.parse(jsonRCVD);
    var arrayElements;
    var objLength;
    var keys;

    jsonOnline = obj;

    if (obj.constructor === Array) {
        arrayElements = Object.keys(obj).length;
        objLength = Object.keys(obj[0]).length;
        keys = Object.keys(obj[0]);
    }
    else if(obj.constructor === Object){
        arrayElements = 1;
        objLength = Object.keys(obj).length;
        keys = Object.keys(obj)
    }

        //var colWidth = Math.round(100 / objLength);
        var colWidth = 80 / (objLength - 1);
        var str = colWidth.toString();
        var realWidthFin = "calc(" + str + "% - 10px)";
        //console.log(realWidthFin);

        var divTable = document.createElement('div');
        divTable.style.textAlign = 'center';
        for (var i = 0; i < objLength; i++) {
            var divKey = document.createElement('div');
            if (i === 0){
                divKey.className = 'tileIFrame';
            }
            else {
                divKey.className = 'tableCol';
            }
            divKey.style.backgroundColor = "#f4bf42";
            divKey.innerHTML = keys[i];
            divTable.appendChild(divKey);
        }

        var divCLR = document.createElement('div');
        divCLR.className = 'clrBoth';
        divTable.appendChild(divCLR);

        for (var j = 0; j < arrayElements; j++) {
            var spanRow = document.createElement('span');
            spanRow.className = 'spanRow';
            for (var k = 0; k < objLength; k++) {

                var divRec = document.createElement('div');
                if (k === 0){
                    divRec.className = 'tileIFrame';
                }
                else {
                    divRec.className = 'tableCol';
                }

                if(obj.constructor === Array){
                    divRec.innerHTML = Object.values(obj[j])[k];
                }
                else if(obj.constructor === Object){
                    divRec.innerHTML = Object.values(obj)[k];
                }

                spanRow.appendChild(divRec);
            }
            divTable.appendChild(spanRow);
            var divCLR2 = document.createElement('div');
            divCLR2.className = 'clrBoth';
            divTable.appendChild(divCLR2);
        }

        document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull').innerHTML = '';
        //document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull').appendChild(divSearchBar);
        document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull').appendChild(divTable);

        for (var l = 0; l < document.getElementById('contentFrame').contentWindow.document.getElementsByClassName('tableCol').length; l++) {
            document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[l].style.width = realWidthFin;
        }


    //callback
    if (typeof callback !== 'function') {
        callback = false;
    }

    if (callback) {
        callback();
    }
    //callback

}

function showDBrecords(whatRecords, callbackFF) {

        var sendEqUrl;
        var showWhat = document.getElementById('subSiteName').innerHTML;

        switch (showWhat) {
            case '#Customers':
                sendEqUrl = "http://localhost:8080/customers/";
                break;
            case '#Orders':
                sendEqUrl = "http://localhost:8080/orders/";
                break;
            case '#Assemblies':
                sendEqUrl = "http://localhost:8080/assemblies/";
                break;
            case '#Parts':
                sendEqUrl = "http://localhost:8080/parts/";
                break;
        }
        sendEqUrl += whatRecords;

        var sendEquationRequest = new XMLHttpRequest();
        sendEquationRequest.open('GET', sendEqUrl, true);
        sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
        sendEquationRequest.onload = function () {
            if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
                // Success!
                var result = sendEquationRequest.responseText;
                //document.getElementById('contentFrame').contentWindow.document.getElementById('response').innerHTML = result;
                showTable(result, callbackFF);
            } else {
                // We reached our target server, but it returned an error
                alert("Could not download data!");
                console.log("Could not download data!");
            }
        };
        sendEquationRequest.send();
    }

function addDelOption(){
    
    var newWidthFinal = "calc(15% - 10px)";
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < document.getElementById('contentFrame').contentWindow.document.getElementsByClassName('spanRow').length; l++) {
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var delButton = document.createElement('div');
        delButton.className = "buttonDel";
        delButton.innerHTML = "X";
        delButton.id = document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].children[0].innerHTML;
        delButton.addEventListener("click", deleteFromDatabase);
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].appendChild(delButton);
       }
}

function deleteFromDatabase(){

    var sendEqUrl;
    var modifyWhat = document.getElementById('subSiteName').innerHTML;

    switch (modifyWhat) {
        case '#Customers':
            sendEqUrl = "http://localhost:8080/customers/del/";
            break;
        case '#Orders':
            sendEqUrl = "http://localhost:8080/orders/del/";
            break;
        case '#Assemblies':
            sendEqUrl = "http://localhost:8080/assemblies/del/";
            break;
        case '#Parts':
            sendEqUrl = "http://localhost:8080/parts/del/";
            break;
    }

    sendEqUrl += this.id;
    var sendEquationRequest = new XMLHttpRequest();
    sendEquationRequest.open('DELETE', sendEqUrl, true);
    sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    sendEquationRequest.onload = function () {
        if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
            // Success!

        } else {
            // We reached our target server, but it returned an error
            alert("Could not download data!");
            console.log("Could not download data!");
        }
    };
    sendEquationRequest.send();
}


function addModifyOption(){

    var newWidthFinal = "calc(15% - 10px)";
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < document.getElementById('contentFrame').contentWindow.document.getElementsByClassName('spanRow').length; l++) {
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var modifyButton = document.createElement('div');
        modifyButton.className = "buttonModify";
        modifyButton.innerHTML = "OK";
        modifyButton.id = document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].children[0].innerHTML;
        modifyButton.addEventListener("click", updateDatabase);
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].appendChild(modifyButton);
    }

    var skipFirstRow = document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[0].getElementsByClassName("tableCol").length;
    for (var i = skipFirstRow; i < document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol").length; i++){
        var inputNewVal = document.createElement("input");
        inputNewVal.value = document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[i].innerHTML;
        inputNewVal.style.width = "60%";
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[i].innerHTML = "";
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[i].style.paddingBottom = "8px";
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[i].style.paddingTop = "8px";
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("tableCol")[i].appendChild(inputNewVal);

    }

}

function updateDatabase(){

    var jsonKeys;
    var jsonValues = [];

    if (jsonOnline.constructor === Array) {
        jsonKeys = Object.keys(jsonOnline[0]);
    }
    else if(jsonOnline.constructor === Object){
        jsonKeys = Object.keys(jsonOnline);
    }

    for (var i = 0; i < this.parentElement.childNodes.length - 1; i++){
        if (i === 0){
            jsonValues.push(this.parentElement.childNodes[i].innerHTML);
        }
        else {
            jsonValues.push(this.parentElement.childNodes[i].childNodes[0].value);
        }
    }

    var jsonToSend = {};
    for (var k = 0; k < jsonKeys.length; k++){
        jsonToSend[jsonKeys[k]] = jsonValues[k];
    }

    console.log(jsonToSend);

    var sendEqUrl;
    var modifyWhat = document.getElementById('subSiteName').innerHTML;
    switch (modifyWhat) {
        case '#Customers':
            sendEqUrl = "http://localhost:8080/customers/";
            break;
        case '#Orders':
            sendEqUrl = "http://localhost:8080/orders/";
            break;
        case '#Assemblies':
            sendEqUrl = "http://localhost:8080/assemblies/";
            break;
        case '#Parts':
            sendEqUrl = "http://localhost:8080/parts/";
            break;
    }

    sendEqUrl += this.id;
    var sendEquationRequest = new XMLHttpRequest();
    sendEquationRequest.open('PUT', sendEqUrl, true);
    sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    sendEquationRequest.onload = function () {
        if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
            // Success!
            var result = sendEquationRequest.responseText;
            console.log('success');
        } else {
            // We reached our target server, but it returned an error
            alert("Could not download data!")
            console.log("Could not download data!");
        }
    };
    var stringified = JSON.stringify(jsonToSend);
    sendEquationRequest.send(stringified);
}