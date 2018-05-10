var jsonOnline;

//---!!DOBRE!!---
function RESTrequest(method, sendEqUrl, jsonToSend, processResponse) {

    var sendEquationRequest = new XMLHttpRequest();
    sendEquationRequest.open(method, sendEqUrl, true);
    sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    sendEquationRequest.onload = function () {
        if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
            var response = sendEquationRequest.responseText;
            if (typeof processResponse !== 'function') {processResponse = false;}
            if (processResponse) {
                var doItWhenReady = function(){
                    processResponse(response);
                };
                doItWhenReady();
            }
        } else {
            alert("Could not download data!");
            console.log("Could not download data!");
        }
    };
    sendEquationRequest.send(JSON.stringify(jsonToSend));
}

//---!!DOBRE!!---
function responseProcessor(rcvd){
    console.log("==========" + rcvd + "===========");
    // nie do konce wiem czemu nie dziala tu z poczatkiem document.getElementById("contentFrame").contentWindow
    //juz wiem: trzeba bylo doadc event listenera zamiast onclicka w kodzie ramki

    var objArray = JSON.parse(rcvd);
    if (objArray.length > 0) {
        showTable(rcvd, function () {
            var co = document.getElementById('optionSiteName').innerHTML;
            switch (co) {
                case "#Show":
                    addSearchBar();
                    //replaceSearchBarWithBetterSearchBar();
                    break;
                case "#Delete":
                    addSearchBar();
                    addDelOption();
                    //replaceSearchBarWithBetterSearchBar();
                    break;
                case "#Modify":
                    addSearchBar();
                    addModifyOption();
                    //replaceSearchBarWithBetterSearchBar();
                    break;
                default:
                    break;
            }
        });
    }
    else {document.getElementById("contentFrame").contentWindow.document.getElementById('iframeContentFull').innerHTML =
        "I found nothing :(";}
}

function submitSearchForm(){

    // var formData = {};
    // $('form [name]').each(function(){
    //     formData[this.name] = this.value;
    // });

    console.log(document.getElementById('contentFrame').contentWindow.document.getElementById('selectSearchType').value);
    console.log(document.getElementById('contentFrame').contentWindow.document.getElementById('textSearchBar').value);

    var formData = {};
    formData.searchType = document.getElementById('contentFrame').contentWindow.document.getElementById('selectSearchType').value; //$('select[name=chooseQuery]').val();
    formData.userInput = document.getElementById('contentFrame').contentWindow.document.getElementById('textSearchBar').value;//$('input[name=userInput]').val();

    console.log(formData);

    RESTrequest('POST', 'http://localhost:8080/parts/search', formData, responseProcessor);
}

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
            alert("Could not download data!");
            console.log("Could not download data!");
        }
    };
    sendEquationRequest.send(stringified);
}

function addSearchBar(){
    var divSearchBar = document.createElement('div');
    divSearchBar.width = "100%";
    divSearchBar.id = "divSearchBar";

    var searchTile = document.createElement('div');
    searchTile.className = 'searchButton';
    searchTile.innerHTML = "<i class = \"icon-search\"></i> Search for:";
    searchTile.addEventListener("click", function(){
        var idToSearchFor = document.getElementById('contentFrame').contentWindow.document.getElementById('entID').value;
        if (document.getElementById('optionSiteName').innerHTML === "#Delete") {
            showDBrecords(idToSearchFor, function(){
                addSearchBar();
                addDelOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
            });
        }
        else if (document.getElementById('optionSiteName').innerHTML === "#Modify") {
            showDBrecords(idToSearchFor, function(){
                addSearchBar();
                addModifyOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
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


function replaceSearchBarWithBetterSearchBar() {

    //testing another way of sending response - using only html form

    // If you want to get the response in a callback, you can't post the form.
    // Posting the form means that the response is loaded as a page.
    // You have to get the form data from the fields in the form and make an AJAX request.
    document.getElementById("contentFrame").contentWindow.document.getElementById('divSearchBar').outerHTML = "";


    //false has to be passed to the form onSubmit, because if it gets false the submission is prevented
    var form =
        "<form action='/parts/all-of-assembly-sorted' method='post' id='searchBarForm' onsubmit='return false'>" +
            "<select name='chooseQuery' id='selectSearchType'>" +
                "<option>searchID</option>" +
                "<option>searchAssembly</option>" +
            "</select>" +
            "<input type='text' name='userInput' value='What are you looking for?' onfocus=\"if (this.value==='What are you looking for?') this.value='';\" id='textSearchBar'/>" +
            "<button id='btnSubmitSearchBar'>Search</button>" +
        "</form>";

    var divBetterSearchBar = document.createElement('div');
    divBetterSearchBar.id = "betterSearchBar";
    divBetterSearchBar.innerHTML = form;

    var frameContent = document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull');
    frameContent.insertBefore(divBetterSearchBar, frameContent.childNodes[0]);

    var divCLR = document.createElement('div');
    divCLR.className = 'clrBoth';
    frameContent.insertBefore(divCLR, frameContent.childNodes[1]);

    document.getElementById("contentFrame").contentWindow.document.getElementById("selectSearchType").style.width =
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tileIFrame")[0].style.width;
    document.getElementById("contentFrame").contentWindow.document.getElementById("textSearchBar").style.width =
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tableCol")[0].style.width;
    document.getElementById("contentFrame").contentWindow.document.getElementById("btnSubmitSearchBar").style.width =
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tableCol")[0].style.width;

    document.getElementById("contentFrame").contentWindow.document.getElementById('btnSubmitSearchBar').addEventListener('click', submitSearchForm);

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

        //this makes better search bar work when called from inned frame (table display option)
        document.getElementById('contentFrame').contentWindow.document.getElementById('iframeContentFull').innerHTML = '';
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

function addObjShowOption(){

    var newWidthFinal = "calc(15% - 10px)";
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < document.getElementById('contentFrame').contentWindow.document.getElementsByClassName('spanRow').length; l++) {
        document.getElementById("contentFrame").contentWindow.document.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var showButton = document.createElement('div');
        showButton.className = "button3d";
        showButton.innerHTML = "Show";
        showButton.id = document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].children[2].innerHTML;
        showButton.addEventListener("click", function(){
            file3DPath = this.id;
            console.log(file3DPath);
            PopupCenter('../frames/3dViewer.html','','900','500');
        });
        document.getElementById('contentFrame').contentWindow.document.getElementsByClassName("spanRow")[l].appendChild(showButton);
       }
}

var file3DPath;

function PopupCenter(url, title, w, h) {
    var left = (screen.width/2)-(w/2);
    var top = (screen.height/2)-(h/2);
    var w =  window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
    w.path = file3DPath;
    return w;
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
            alert("Could not download data!");
            console.log("Could not download data!");
        }
    };
    var stringified = JSON.stringify(jsonToSend);
    sendEquationRequest.send(stringified);
}
