var jsonOnline;

function RESTrequest(method, sendEqUrl, jsonToSend, processResponse, callback) {

    var sendEquationRequest = new XMLHttpRequest();
    sendEquationRequest.open(method, sendEqUrl, true);
    sendEquationRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    sendEquationRequest.onload = function () {
        if (sendEquationRequest.status >= 200 && sendEquationRequest.status < 400) {
            var response = sendEquationRequest.responseText;
            if (typeof processResponse !== 'function') {
                processResponse = false;
            }
            if (processResponse) {
                var doItWhenReady = function () {
                    if (typeof callback !== 'function') {
                        callback = false;
                        processResponse(response);
                    }
                    if (callback) {
                        processResponse(response, callback);
                    }
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

function getSubSiteAddress(manuallySet){

    var modifyWhat;
    if (manuallySet !== undefined){modifyWhat = manuallySet;}
    else {modifyWhat = document.getElementById('subSiteName').innerHTML;}

    var domain = window.location.origin;

    var sendEqUrl = '';
    switch (modifyWhat) {
        case '#Customers':
            sendEqUrl = domain + "/customers/";
            break;
        case '#Orders':
            sendEqUrl = domain + "/orders/";
            break;
        case '#Assemblies':
            sendEqUrl = domain + "/assemblies/";
            break;
        case '#Parts':
            sendEqUrl = domain + "/parts/";
            break;
    }
    return sendEqUrl;
}

function showTable(jsonRCVD, callback) {

    var frameDoc = document.getElementById("contentFrame").contentWindow.document;

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
    else if (obj.constructor === Object) {
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
        if (i === 0) {
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
            if (k === 0) {
                divRec.className = 'tileIFrame';
            }
            else {
                divRec.className = 'tableCol';
            }

            if (obj.constructor === Array) {
                divRec.innerHTML = Object.values(obj[j])[k];
            }
            else if (obj.constructor === Object) {
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
    frameDoc.getElementById('iframeContentFull').innerHTML = '';
    frameDoc.getElementById('iframeContentFull').appendChild(divTable);

    for (var l = 0; l < frameDoc.getElementsByClassName('tableCol').length; l++) {
        frameDoc.getElementsByClassName("tableCol")[l].style.width = realWidthFin;
    }

    //callback
    if (typeof callback !== 'function') {
        callback = false;
    }

    if (callback) {
        callback();
    }

}

function requestDataAndShowDBRecords(whatRecords, callbackFF) {

    var sendEqUrl = getSubSiteAddress();
    sendEqUrl += whatRecords;
    RESTrequest("GET", sendEqUrl, "", showTable, callbackFF);
}

function addToDatabase(addWhat) {

    var oElements = {};
    $('form [name]').each(function () {
        oElements[this.name] = this.value;
    });

    var sendEqUrl = getSubSiteAddress(addWhat);
    RESTrequest("POST", sendEqUrl, oElements, "", "");
}

function updateDatabase() {

    var jsonKeys;
    var jsonValues = [];

    if (jsonOnline.constructor === Array) {
        jsonKeys = Object.keys(jsonOnline[0]);
    }
    else if (jsonOnline.constructor === Object) {
        jsonKeys = Object.keys(jsonOnline);
    }

    for (var i = 0; i < this.parentElement.childNodes.length - 1; i++) {
        if (i === 0) {
            jsonValues.push(this.parentElement.childNodes[i].innerHTML);
        }
        else {
            jsonValues.push(this.parentElement.childNodes[i].childNodes[0].value);
        }
    }

    var jsonToSend = {};
    for (var k = 0; k < jsonKeys.length; k++) {
        jsonToSend[jsonKeys[k]] = jsonValues[k];
    }

    console.log(jsonToSend);

    var sendEqUrl = getSubSiteAddress();
    sendEqUrl += this.id;

    RESTrequest("PUT", sendEqUrl, jsonToSend, "", "");
}

function deleteFromDatabase() {
    var sendEqUrl = getSubSiteAddress();
    sendEqUrl += "del/";
    sendEqUrl += this.id;
    RESTrequest("DELETE", sendEqUrl, "", "", "");
}

function addSearchBar() {
    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    var divSearchBar = document.createElement('div');
    divSearchBar.width = "100%";
    divSearchBar.id = "divSearchBar";

    var searchTile = document.createElement('div');
    searchTile.className = 'searchButton';
    searchTile.innerHTML = "<i class = \"icon-search\"></i> Search for:";
    searchTile.addEventListener("click", function () {
        var idToSearchFor = frameDoc.getElementById('entID').value;
        if (document.getElementById('optionSiteName').innerHTML === "#Delete") {
            requestDataAndShowDBRecords(idToSearchFor, function () {
                addSearchBar();
                addDelOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
            });
        }
        else if (document.getElementById('optionSiteName').innerHTML === "#Modify") {
            requestDataAndShowDBRecords(idToSearchFor, function () {
                addSearchBar();
                addModifyOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
            });
        }
        else {
            requestDataAndShowDBRecords(idToSearchFor);
        }
    });
    divSearchBar.appendChild(searchTile);

    var searchWhat = document.createElement('div');
    searchWhat.className = "frameInput";
    searchWhat.innerHTML = "<input id=\"entID\" style=\"background-color: #8eb9ff;\" type=\"text\" name=\"toSearchID\" value=\"Enter ID\" onfocus=\"if (this.value==='Enter ID') this.value='';\">";
    divSearchBar.appendChild(searchWhat);

    var frameContent = frameDoc.getElementById('iframeContentFull');
    frameContent.insertBefore(divSearchBar, frameContent.childNodes[0]);
    //frameDoc.getElementById('iframeContentFull').appendChild(divSearchBar);
}

function addModifyOption() {
    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    var newWidthFinal = "calc(15% - 10px)";
    frameDoc.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    frameDoc.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < frameDoc.getElementsByClassName('spanRow').length; l++) {
        frameDoc.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var modifyButton = document.createElement('div');
        modifyButton.className = "buttonModify";
        modifyButton.innerHTML = "OK";
        modifyButton.id = frameDoc.getElementsByClassName("spanRow")[l].children[0].innerHTML;
        modifyButton.addEventListener("click", updateDatabase);
        frameDoc.getElementsByClassName("spanRow")[l].appendChild(modifyButton);
    }

    var skipFirstRow = frameDoc.getElementsByClassName("spanRow")[0].getElementsByClassName("tableCol").length;
    for (var i = skipFirstRow; i < frameDoc.getElementsByClassName("tableCol").length; i++) {
        var inputNewVal = document.createElement("input");
        inputNewVal.value = frameDoc.getElementsByClassName("tableCol")[i].innerHTML;
        inputNewVal.style.width = "60%";
        frameDoc.getElementsByClassName("tableCol")[i].innerHTML = "";
        frameDoc.getElementsByClassName("tableCol")[i].style.paddingBottom = "8px";
        frameDoc.getElementsByClassName("tableCol")[i].style.paddingTop = "8px";
        frameDoc.getElementsByClassName("tableCol")[i].appendChild(inputNewVal);
    }
}

function addDelOption() {

    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    var newWidthFinal = "calc(15% - 10px)";
    frameDoc.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    frameDoc.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < frameDoc.getElementsByClassName('spanRow').length; l++) {
        frameDoc.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var delButton = document.createElement('div');
        delButton.className = "buttonDel";
        delButton.innerHTML = "X";
        delButton.id = frameDoc.getElementsByClassName("spanRow")[l].children[0].innerHTML;
        delButton.addEventListener("click", deleteFromDatabase);
        frameDoc.getElementsByClassName("spanRow")[l].appendChild(delButton);
    }
}

function addObjShowOption() {

    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    var newWidthFinal = "calc(15% - 10px)";
    frameDoc.getElementsByClassName("tileIFrame")[0].style.width = newWidthFinal;
    frameDoc.getElementsByClassName("searchButton")[0].style.width = newWidthFinal;
    for (var l = 0; l < frameDoc.getElementsByClassName('spanRow').length; l++) {
        frameDoc.getElementsByClassName("spanRow")[l].children[0].style.width = newWidthFinal;
        var showButton = document.createElement('div');
        showButton.className = "button3d";
        showButton.innerHTML = "Show";
        showButton.id = frameDoc.getElementsByClassName("spanRow")[l].children[2].innerHTML;
        showButton.addEventListener("click", function () {
            file3DPath = this.id;
            console.log(file3DPath);
            PopupCenter('../frames/3dViewer.html', '', '900', '500');
        });
        frameDoc.getElementsByClassName("spanRow")[l].appendChild(showButton);
    }
}

var file3DPath;

function PopupCenter(url, title, w, h) {
    var left = (screen.width / 2) - (w / 2);
    var top = (screen.height / 2) - (h / 2);
    var wind = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    wind.path = file3DPath;
    return wind;
}

function replaceSearchBarWithBetterSearchBar() {

    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    //testing another way of sending response - using only html form

    // If you want to get the response in a callback, you can't post the form.
    // Posting the form means that the response is loaded as a page.
    // You have to get the form data from the fields in the form and make an AJAX request.
    frameDoc.getElementById('divSearchBar').outerHTML = "";


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

    var frameContent = frameDoc.getElementById('iframeContentFull');
    frameContent.insertBefore(divBetterSearchBar, frameContent.childNodes[0]);

    var divCLR = document.createElement('div');
    divCLR.className = 'clrBoth';
    frameContent.insertBefore(divCLR, frameContent.childNodes[1]);

    frameDoc.getElementById("selectSearchType").style.width = frameDoc.getElementsByClassName("tileIFrame")[0].style.width;
    frameDoc.getElementById("textSearchBar").style.width = frameDoc.getElementsByClassName("tableCol")[0].style.width;
    frameDoc.getElementById("btnSubmitSearchBar").style.width = frameDoc.getElementsByClassName("tableCol")[0].style.width;

    frameDoc.getElementById('btnSubmitSearchBar').addEventListener('click', submitSearchForm);
}

function submitSearchForm() {
    var frameDoc = document.getElementById("contentFrame").contentWindow.document;
    var formData = {};
    formData.searchType = frameDoc.getElementById('selectSearchType').value;
    formData.userInput = frameDoc.getElementById('textSearchBar').value;
    RESTrequest('POST', window.location.href + 'parts/search', formData, responseProcessor);
}

function responseProcessor(rcvd) {
    console.log("==========" + rcvd + "===========");
    // nie do konce wiem czemu nie dziala tu z poczatkiem document.getElementById("contentFrame").contentWindow
    //juz wiem: trzeba bylo doadc event listenera zamiast onclicka w kodzie ramki

    var frameDoc = document.getElementById("contentFrame").contentWindow.document;

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
    else {
        frameDoc.getElementById('iframeContentFull').innerHTML =
            "I found nothingggg :(";
    }
}

