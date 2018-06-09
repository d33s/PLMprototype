$(document).ready(function () {

    $("#btnSubmitToAssemblies").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit("/assemblies/upload");
    });

    $("#btnSubmitToWarehouse").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit("/warehouse/upload");
    });

    $("#btnSubmitToParts").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        var oElements = {};
        $('form [name]').each(function () {
            oElements[this.name] = this.value;
        });

        fire_ajax_submit("/assemblies/" + oElements.assemblyID + "/parts/upload");
    });

});

function fire_ajax_submit(urlToSend) {

    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    data.append("CustomField", "This is some extra data, testing");

    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: urlToSend,
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#response").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#response").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });

}
