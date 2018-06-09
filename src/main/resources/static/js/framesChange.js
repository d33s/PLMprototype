function changeFrame(frameName) {
    document.getElementById('contentFrame').src = frameName;
}

function changeTitle(title) {
    document.getElementById('subSiteName').innerHTML = title;
}

function changeOption(title) {
    document.getElementById('optionSiteName').innerHTML = title;
}

function show() {
    var showWhat = document.getElementById('subSiteName').innerHTML;
    switch (showWhat) {
        case '#Customers':
            document.getElementById('contentFrame').src = "frames/customers.html";
            break;
        case '#Orders':
            document.getElementById('contentFrame').src = "frames/orders.html";
            break;
        case '#Assemblies':
            document.getElementById('contentFrame').src = "frames/assemblies.html";
            break;
        case '#Parts':
            document.getElementById('contentFrame').src = "frames/parts.html";
            break;
        case '#Employees':
            document.getElementById('contentFrame').src = "frames/employees.html";
            break;
        case '#Warehouse':
            document.getElementById('contentFrame').src = "frames/warehouse.html";
            break;
    }
}

function changeFrameContext(operationName) {

    switch (operationName) {
        case 'Show':
            requestDataAndShowDBRecords("all", function () {
                    addSearchBar();
                    if (document.getElementById('subSiteName').innerHTML === "#Assemblies") {
                        addObjShowOption();
                    }
                    else if (document.getElementById('subSiteName').innerHTML === "#Warehouse"){
                        addPdfShowOption();
                    }
                    else if (document.getElementById('subSiteName').innerHTML === "#Parts"){
                        addPdfShowOption();
                    }

                    if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                        replaceSearchBarWithBetterSearchBar();
                    }
                }
            );
            break;
        case 'Add':
            show();
            break;
        case 'Delete':
            requestDataAndShowDBRecords("all", function () {
                addSearchBar();
                addDelOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
            });
            break;
        case 'Modify':
            var assemblyOrNot;
            if (document.getElementById('subSiteName').innerHTML === "#Assemblies" || document.getElementById('subSiteName').innerHTML === "#Warehouse") {
                assemblyOrNot = "all/modif";
            }
            else {
                assemblyOrNot = "all"
            }

            requestDataAndShowDBRecords(assemblyOrNot, function () {
                addSearchBar();
                addModifyOption();
                if (document.getElementById('subSiteName').innerHTML === "#Parts") {
                    replaceSearchBarWithBetterSearchBar();
                }
            });
            break;
        default:
            break;
    }
    changeOption("#" + operationName);
}

