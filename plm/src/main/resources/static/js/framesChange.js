function changeFrame(frameName){
	document.getElementById('contentFrame').src = frameName;
}

function changeTitle(title){
    document.getElementById('subSiteName').innerHTML = title;
}

function changeOption(title){
    document.getElementById('optionSiteName').innerHTML = title;
}

function show(){

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
    }
}

function changeFrameContext(operationName){

    switch (operationName) {
        case 'Show':
            showDBrecords("all", addSearchBar);
            break;
        case 'Add':
            show();
            break;
        case 'Delete':
            showDBrecords("all", function(){
                addSearchBar();
                addDelOption();
            });
            break;
        case 'Modify':
            showDBrecords("all", function(){
                addSearchBar();
                addModifyOption();
            });
            break;
        default:
            break;
    }
    changeOption("#"+operationName);
}

