function initColumnChart() {
    var keyPagesViews = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valuePagesViews = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var copyPagesViews;
    var highestPagesViews;
    var i = 0;
    $.ajax({
        type: "GET",
        url: "/console/getPagesViews",
        dataType: "json",
        success: function (response) {
            var graphData = jQuery.parseJSON(JSON.stringify(response));
            var c = 0;
            for (var key in graphData) {
                keyPagesViews.push("" + key);
                valuePagesViews.push(graphData[key]);
                if (graphData[key] > i) {
                    i = graphData[key];
                }
            }
            copyPagesViews = valuePagesViews;
            highestPagesViews = i;
        },
        error: function () {
            alert("Errore getMonthViewsJava");
        }
    });

    var colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#546E7A', '#26a69a', '#D10CE8'];
    var options = {
        chart: {
            height: 350,
            type: 'bar',
            events: {
                click: function (chart, w, e) {
                    console.log(chart, w, e)
                }
            },
        },
        colors: colors,
        plotOptions: {
            bar: {
                columnWidth: '45%',
                distributed: true
            }
        },
        dataLabels: {
            enabled: false,
        },
        series: [{
                name: 'Visualizzazioni',
                data: valuePagesViews
            }],
        xaxis: {
            categories: keyPagesViews,
            labels: {
                style: {
                    colors: colors,
                    fontSize: '14px'
                }
            }
        }
    }

    var chart = new ApexCharts(
            document.querySelector("#columnChart"),
            options
            );
    chart.render();
}

function initPieChart() {
    var keyPieProduct = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valuePieProduct = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var copyPieProduct;

    $.ajax({
        type: "GET",
        url: "/console/getProductBuy",
        dataType: "json",
        success: function (response) {
            var graphData = jQuery.parseJSON(JSON.stringify(response));
            for (var key in graphData) {
                keyPieProduct.push("" + key);
                valuePieProduct.push(graphData[key]);
            }
            var options = {
                chart: {
                    height: 350,
                    type: 'pie',
                },
                labels: keyPieProduct,
                series: valuePieProduct,
                responsive: [{
                        breakpoint: 480,
                        options: {
                            chart: {
                                width: 200
                            },
                            legend: {
                                position: 'bottom'
                            }
                        }
                    }]
            }

            var chart = new ApexCharts(
                    document.querySelector("#pieProduct"),
                    options
                    );

            chart.render();
        },
        error: function () {
            alert("Errore getProductBuy");
        }
    });
}
