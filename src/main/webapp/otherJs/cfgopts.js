/**
 * 格式化data，data格式如:[{name:nameValue,value:valueVal},...]
 *
 * @param data
 * @returns {object}
 */
formtBarData: function (data) {
    var xAxData = [];
    var serData = [];

    for (var i = 0; i < data.length; i++) {
        xAxData.push(data[i].name || "");
        serData.push({
            name: data[i].name,
            value: data[i].value || 0
        });
    }

    return {
        xAxData: xAxData,
        serData: serData
    };
}
