function justPrint(resp) {
    console.log(resp);
}

function fillFields(response){

    var obj = JSON.parse(response);
    document.getElementById("summaryOrderId").innerHTML = obj.id;
    document.getElementById("summaryDate").innerHTML = obj.orderDate.toString();
    document.getElementById("summaryCustomer").innerHTML = obj.customerID.toString();
    document.getElementById("summaryAssembly").innerHTML = obj.assemblyID.toString();
    document.getElementById("summaryEmployees").innerHTML = obj.peopleResponsible.toString();
    document.getElementById("summaryStatus").innerHTML = obj.status;

    var statForProgress = obj.status;
    var howManyMakeGreen = 0;

    switch(statForProgress) {
        case "planning":
            howManyMakeGreen = 1;
            break;
        case "primaryDesign":
            howManyMakeGreen = 2;
            break;
        case "primaryDesignTesting":
            howManyMakeGreen = 3;
            break;
        case "improvements":
            howManyMakeGreen = 4;
            break;
        case "finalDesign":
            howManyMakeGreen = 5;
            break;
        case "production":
            howManyMakeGreen = 6;
            break;
        case "assembling":
            howManyMakeGreen = 7;
            break;
        case "finalTests":
            howManyMakeGreen = 8;
            break;
        case "inWarehouse":
            howManyMakeGreen = 9;
            break;
        case "released":
            howManyMakeGreen = 10;
            break;
        case "returnedForConservation":
            howManyMakeGreen = 11;
            break;
         default:
            howManyMakeGreen = 0;
    }

    for (var i = 0; i < document.getElementById("progressBar").children.length; i++){
        document.getElementById("progressBar").children[i].style.backgroundColor = "grey";
    }

    for (var j = 0; j < howManyMakeGreen; j++){
        document.getElementById("progressBar").children[j].style.backgroundColor = "greenyellow";
    }

    var assID = obj.assemblyID.toString();
    var url = "/assemblies/" + assID + "/parts";
    RESTrequest("GET", url, "", summaryShowTable, "");
}

function requestData(){
    var id = document.getElementById("summaryOrderInput").value;
    var url = "/orders/" + id;
    console.log(url);
    RESTrequest("GET", url, "", fillFields, "");
}

function summaryShowTable(jsonRCVD, callback) {

    if(jsonRCVD === "[]"){document.getElementById('summaryTable').innerHTML = 'Assembly of this order has no parts.';}
    else{
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

        //this makes better search bar work when called from inner frame (table display option)
        document.getElementById('summaryTable').innerHTML = '';
        document.getElementById('summaryTable').appendChild(divTable);

        for (var l = 0; l < document.getElementsByClassName('tableCol').length; l++) {
            document.getElementsByClassName("tableCol")[l].style.width = realWidthFin;
        }

        //callback
        if (typeof callback !== 'function') {
            callback = false;
        }

        if (callback) {
            callback();
        }
    }

}