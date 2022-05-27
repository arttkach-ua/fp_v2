    $(document).ready(function () {
        $('#car_brand').change(function () {
            $('#car_model').find('option').remove();
            $('#car_model').append('<option>Select car model</option>');

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
                        $('#car_model').append('<option value="' + value.id + '">' + value.modelName + '</option>')
                    });
                    $('select').formSelect();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#car_model').append('<option>Car model Unavailable</option>');
                },
                cache: false
            });
        });
    });
