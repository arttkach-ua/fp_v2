function setCarModels() {
        $('#car_model').find('option').remove();
        $('#car_model').append('<option><fmt:message key="select_general_placeholder"/></option>');

        let brand_id = $('#car_brand').val();
        let data = {
            operation: "car_model",
            id: brand_id
        };
        $.ajax({
            url: "TestServlet",
            method: "GET",
            data: data,
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                let obj = $.parseJSON(data);
                $.each(obj, function (key, value) {
                    $('#car_model').append('<option value="' + value.ID + '">' + value.modelName + '</option>')
                });
                //$('select').formSelect();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#car_model').append('<option>Car model Unavailable</option>');
            },
            cache: false
        });
    }
function setCompleteSets() {
    $('#complete_set').find('option').remove();
    $('#complete_set').append('<option><fmt:message key="select_general_placeholder"/></option>');

    let model_id = $('#car_model').val();
    let data = {
        operation: "complete_set",
        id: model_id
    };
    $.ajax({
        url: "GetCompleteSets",
        method: "GET",
        data: data,
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            let obj = $.parseJSON(data);
            $.each(obj, function (key, value) {
                $('#complete_set').append('<option value="' + value.ID + '">' + value.name + '</option>')
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#complete_set').append('<option>Complete set Unavailable</option>');
        },
        cache: false
    });

}