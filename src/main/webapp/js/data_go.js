function sendWithPost(url, data, eventHandler, errorHandler) {
    $.ajax({
        url: url,
        data: data,
        method: "POST",
        cache: false,
        dataType: "json",
        success: (data, textStatus, jqXHR) => eventHandler(data),
        error: (jqXHR, textStatus, errorThrown) => errorHandler(jqXHR, errorThrown)
    });
}

function sendWithGet(url, data, eventHandler, errorHandler) {
    $.ajax({
        url: url,
        data: data,
        method: "GET",
        cache: false,
        dataType: "json",
        success: (data, textStatus, jqXHR) => eventHandler(data),
        error: (jqXHR, textStatus, errorThrown) => errorHandler(jqXHR, errorThrown)
    });
}