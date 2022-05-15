$(document).ready(function () {
    $(document).on("click", ".btnRemove", function (event) {
        console.log($(this).data("itemid"));
        $.ajax(
                {
                    url: "/app/CardAPI",
                    type: "DELETE",
                    data: "cardId=" + $(this).data("itemid"),
                    dataType: "text",

                    complete: function (response, status)
                    {
                        console.log(response);
                    }
                });
    });

    $(document).on("click", ".btnUpdate", function (event) {
        var id = +this.id;
        console.log(id, this);
        var $row = $(this).closest("tr");
        console.log($row);
        $.ajax(
                {
                    url: "/app/CardAPI",
                    type: "put",
                    data: "cardId=" + id + "&card=",
                    dataType: "text",

                    complete: function (response, status)
                    {
                        console.log(response)
                    }
                });


    });
});