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
            console.log("Errore getMonthViewsJava");
        }
    });

    var colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#546E7A', '#26a69a', '#D10CE8'];
    var options = {
        chart: {
            height: 350,
            type: 'bar',
            events: {
                click: function (chart, w, e) {
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
            console.log("Errore getProductBuy");
        }
    });
}



/* Grafico per le statistiche */
function initBlogViewChart(tipo) {


    var keyBlogViews = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueBlogViews = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var keyBlogArtViews = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueBlogArtViews = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var copyBlogViews;
    var i = 0;
    var arrayData = [], dataYearSeries = [], quart = [];
    
    Apex = {
        chart: {
            toolbar: {
                show: true
            }
        },
        tooltip: {
            shared: true,
            followCursor: true
        }
    };
    var colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3'];

    /**
     * Randomize array element order in-place.
     * Using Durstenfeld shuffle algorithm.
     */
    function shuffleArray(array) {
        for (var i = array.length - 1; i > 0; i--) {
            var j = Math.floor(Math.random() * (i + 1));
            var temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }
    
    $.ajax({
        type: "GET",
        url: "/console/getBlogCatViews",
        data: {tipo: tipo},
        dataType: "json",
        success: function (response) {
            var graphDataCat = jQuery.parseJSON(JSON.stringify(response));
            var c = 0;

            for (var keyCat in graphDataCat) {
                keyBlogViews.push("" + keyCat);
                valueBlogViews.push(graphDataCat[keyCat]);
            }
            var r = null;
            for (var c = 0; c < valueBlogViews.length; c++) {
                quart = [];
                keyBlogArtViews = [];
                valueBlogArtViews = [];
                var tmp = null;
                r = function () {
                    $.ajax({
                        async: false,
                        type: "GET",
                        url: "/console/getBlogArtViews",
                        data: {categoria: keyBlogViews[c], tipo: tipo},
                        dataType: "json",
                        success: function (response) {
                            tmp = response;
                        },
                        error: function () {
                            console.log("Errore getArtViews");
                        }
                    });
                    return tmp;
                }();
                
                var graphDataArt = jQuery.parseJSON(JSON.stringify(r));
                for (var keyArt in graphDataArt) {
                    keyBlogArtViews.push("" + keyArt);
                    valueBlogArtViews.push(graphDataArt[keyArt]);
                }

                for (var d = 0; d < valueBlogArtViews.length; d++) {
                    quart[d] = {
                        x: keyBlogArtViews[d],
                        y: valueBlogArtViews[d]
                    };
                }


                arrayData[c] = {
                    y: valueBlogViews[c], /* Valore (Anno) medio della categoria */
                    quarters: quart
                };
            }

            var l = valueBlogViews.length / 7;
            var arrotondato = Math.round(l);
            for (var i = 0; i < arrotondato - 1; i++) {
                colors.push('#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3');
            }

            function makeData() {
                for (var c = 0; c < valueBlogViews.length; c++) {
                    dataYearSeries[c] = {
                        x: keyBlogViews[c],
                        y: arrayData[c].y,
                        color: colors[c],
                        quarters: arrayData[c].quarters
                    };
                }
                return dataYearSeries;
            }

            var optionsYear = {
                chart: {
                    id: 'barYear',
                    height: 400,
                    width: '100%',
                    type: 'bar',
                },
                plotOptions: {
                    bar: {
                        distributed: true,
                        horizontal: true,
                        barHeight: '75%',
                        dataLabels: {
                            position: 'bottom'
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    textAnchor: 'start',
                    style: {
                        colors: ['#fff']
                    },
                    formatter: function (val, opt) {
                        return opt.w.globals.labels[opt.dataPointIndex]
                    },
                    offsetX: 0,
                    dropShadow: {
                        enabled: true
                    }
                },

                colors: colors,
                series: [{
                        data: makeData()
                    }],
                states: {
                    normal: {
                        filter: {
                            type: 'desaturate'
                        }
                    },
                    active: {
                        allowMultipleDataPointsSelection: false,
                        filter: {
                            type: 'darken',
                            value: 1
                        }
                    }
                },
                tooltip: {
                    x: {
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                },
                title: {
                    text: 'Letture per categoria',
                    offsetX: 15
                },
                subtitle: {
                    text: '(Clicca sulle barre per vedere e confrontare i dettagli)',
                    offsetX: 15
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                }
            };

            var yearChart = new ApexCharts(
                    document.querySelector("#chart-year-"+tipo),
                    optionsYear
                    );

            yearChart.render();

            function updateQuarterChart(sourceChart, destChartIDToUpdate) {
                var series = [];
                var seriesIndex = 0;
                var colors = []

                if (sourceChart.w.globals.selectedDataPoints[0]) {
                    var selectedPoints = sourceChart.w.globals.selectedDataPoints;
                    for (var i = 0; i < selectedPoints[seriesIndex].length; i++) {
                        var selectedIndex = selectedPoints[seriesIndex][i];
                        var yearSeries = sourceChart.w.config.series[seriesIndex];
                        series.push({
                            name: yearSeries.data[selectedIndex].x,
                            data: yearSeries.data[selectedIndex].quarters
                        })
                        colors.push(yearSeries.data[selectedIndex].color)
                    }

                    if (series.length === 0)
                        series = [{
                                data: []
                            }]

                    return ApexCharts.exec(destChartIDToUpdate, 'updateOptions', {
                        series: series,
                        colors: colors,
                        fill: {
                            colors: colors
                        }
                    })

                }

            }


            var optionsQuarters = {
                chart: {
                    id: 'barQuarter-'+tipo,
                    height: 400,
                    width: '100%',
                    type: 'bar',
                    stacked: true
                },
                plotOptions: {
                    bar: {
                        columnWidth: '50%',
                        horizontal: false
                    }
                },
                series: [{
                        data: []
                    }],
                legend: {
                    show: true
                },
                grid: {
                    yaxis: {
                        lines: {
                            show: false,
                        }
                    },
                    xaxis: {
                        lines: {
                            show: true,
                        }
                    }
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                },
                title: {
                    text: 'Letture per articolo',
                    offsetX: 10
                },
                tooltip: {
                    x: {
                        /*formatter: function (val, opts) {
                            return opts.w.globals.seriesNames[opts.seriesIndex]
                        } */
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                }
            };

            var chartQuarters = new ApexCharts(
                    document.querySelector("#chart-quarter-"+tipo),
                    optionsQuarters
                    );
            chartQuarters.render();

            yearChart.addEventListener('dataPointSelection', function (e, chart, opts) {
                var quarterChartEl = document.querySelector("#chart-quarter-"+tipo);
                var yearChartEl = document.querySelector("#chart-year-"+tipo);

                if (opts.selectedDataPoints[0].length === 1) {
                    if (quarterChartEl.classList.contains("active")) {
                        updateQuarterChart(chart, 'barQuarter-'+tipo)
                    } else {
                        yearChartEl.classList.add("chart-quarter-activated")
                        quarterChartEl.classList.add("active");
                        updateQuarterChart(chart, 'barQuarter-'+tipo)
                    }
                } else {
                    updateQuarterChart(chart, 'barQuarter-'+tipo)
                }

                if (opts.selectedDataPoints[0].length === 0) {
                    yearChartEl.classList.remove("chart-quarter-activated")
                    quarterChartEl.classList.remove("active");
                }

            })

            yearChart.addEventListener('updated', function (chart) {
                updateQuarterChart(chart, 'barQuarter-'+tipo)
            })


            document.querySelector("#app-"+tipo).addEventListener("change", function (e) {
                yearChart.updateSeries([{
                        data: makeData()
                    }])
            })

        },
        error: function () {
            console.log("Errore getBlogViews");
        }
    });
}


/* Grafico per le statistiche dei commenti */
function initCommentChart(tipo) {


    var keyComment = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueComment = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var keyArtComment = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueArtComment = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var arrayData = [], dataYearSeries = [], quart = [];
    
    Apex = {
        chart: {
            toolbar: {
                show: true
            }
        },
        tooltip: {
            shared: true,
            followCursor: true
        }
    };
    var colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3'];

    
    $.ajax({
        type: "GET",
        url: "/console/getCatComment",
        data: {tipo: tipo},
        dataType: "json",
        success: function (response) {
            var graphDataCat = jQuery.parseJSON(JSON.stringify(response));
            var c = 0;

            for (var keyCat in graphDataCat) {
                keyComment.push("" + keyCat);
                valueComment.push(graphDataCat[keyCat]);
            }
            var r = null;
            for (var c = 0; c < valueComment.length; c++) {
                quart = [];
                keyArtComment = [];
                valueArtComment = [];
                var tmp = null;
                r = function () {
                    $.ajax({
                        async: false,
                        type: "GET",
                        url: "/console/getArtComments",
                        data: {categoria: keyComment[c], tipo: tipo},
                        dataType: "json",
                        success: function (response) {
                            tmp = response;
                        },
                        error: function () {
                            console.log("Errore getArtViews");
                        }
                    });
                    return tmp;
                }();
                
                var graphDataArt = jQuery.parseJSON(JSON.stringify(r));
                for (var keyArt in graphDataArt) {
                    keyArtComment.push("" + keyArt);
                    valueArtComment.push(graphDataArt[keyArt]);
                }

                for (var d = 0; d < valueArtComment.length; d++) {
                    quart[d] = {
                        x: keyArtComment[d],
                        y: valueArtComment[d]
                    };
                }


                arrayData[c] = {
                    y: valueComment[c], /* Valore (Anno) medio della categoria */
                    quarters: quart
                };
            }

            var l = valueComment.length / 7;
            var arrotondato = Math.round(l);
            for (var i = 0; i < arrotondato - 1; i++) {
                colors.push('#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3');
            }

            function makeData() {
                for (var c = 0; c < valueComment.length; c++) {
                    dataYearSeries[c] = {
                        x: keyComment[c],
                        y: arrayData[c].y,
                        color: colors[c],
                        quarters: arrayData[c].quarters
                    };
                }
                return dataYearSeries;
            }

            var optionsYear = {
                chart: {
                    id: 'barYear',
                    height: 400,
                    width: '100%',
                    type: 'bar',
                },
                plotOptions: {
                    bar: {
                        distributed: true,
                        horizontal: true,
                        barHeight: '75%',
                        dataLabels: {
                            position: 'bottom'
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    textAnchor: 'start',
                    style: {
                        colors: ['#fff']
                    },
                    formatter: function (val, opt) {
                        return opt.w.globals.labels[opt.dataPointIndex]
                    },
                    offsetX: 0,
                    dropShadow: {
                        enabled: true
                    }
                },

                colors: colors,
                series: [{
                        data: makeData()
                    }],
                states: {
                    normal: {
                        filter: {
                            type: 'desaturate'
                        }
                    },
                    active: {
                        allowMultipleDataPointsSelection: false,
                        filter: {
                            type: 'darken',
                            value: 1
                        }
                    }
                },
                tooltip: {
                    x: {
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                },
                title: {
                    text: 'Commenti per categoria',
                    offsetX: 15
                },
                subtitle: {
                    text: '(Clicca sulle barre per vedere e confrontare i dettagli)',
                    offsetX: 15
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                }
            };

            var yearChart = new ApexCharts(
                    document.querySelector("#chart-year-"+tipo+"-comments"),
                    optionsYear
                    );

            yearChart.render();

            function updateQuarterChart(sourceChart, destChartIDToUpdate) {
                var series = [];
                var seriesIndex = 0;
                var colors = []

                if (sourceChart.w.globals.selectedDataPoints[0]) {
                    var selectedPoints = sourceChart.w.globals.selectedDataPoints;
                    for (var i = 0; i < selectedPoints[seriesIndex].length; i++) {
                        var selectedIndex = selectedPoints[seriesIndex][i];
                        var yearSeries = sourceChart.w.config.series[seriesIndex];
                        series.push({
                            name: yearSeries.data[selectedIndex].x,
                            data: yearSeries.data[selectedIndex].quarters
                        })
                        colors.push(yearSeries.data[selectedIndex].color)
                    }

                    if (series.length === 0)
                        series = [{
                                data: []
                            }]

                    return ApexCharts.exec(destChartIDToUpdate, 'updateOptions', {
                        series: series,
                        colors: colors,
                        fill: {
                            colors: colors
                        }
                    })

                }

            }


            var optionsQuarters = {
                chart: {
                    id: 'barQuarter-'+tipo+"-comments",
                    height: 400,
                    width: '100%',
                    type: 'bar',
                    stacked: true
                },
                plotOptions: {
                    bar: {
                        columnWidth: '50%',
                        horizontal: false
                    }
                },
                series: [{
                        data: []
                    }],
                legend: {
                    show: true
                },
                grid: {
                    yaxis: {
                        lines: {
                            show: false,
                        }
                    },
                    xaxis: {
                        lines: {
                            show: true,
                        }
                    }
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                },
                title: {
                    text: 'Commenti per articolo',
                    offsetX: 10
                },
                tooltip: {
                    x: {
                        /*formatter: function (val, opts) {
                            return opts.w.globals.seriesNames[opts.seriesIndex]
                        } */
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                }
            };

            var chartQuarters = new ApexCharts(
                    document.querySelector("#chart-quarter-"+tipo+"-comments"),
                    optionsQuarters
                    );
            chartQuarters.render();

            yearChart.addEventListener('dataPointSelection', function (e, chart, opts) {
                var quarterChartEl = document.querySelector("#chart-quarter-"+tipo+"-comments");
                var yearChartEl = document.querySelector("#chart-year-"+tipo+"-comments");

                if (opts.selectedDataPoints[0].length === 1) {
                    if (quarterChartEl.classList.contains("active")) {
                        updateQuarterChart(chart, 'barQuarter-'+tipo+"-comments")
                    } else {
                        yearChartEl.classList.add("chart-quarter-activated")
                        quarterChartEl.classList.add("active");
                        updateQuarterChart(chart, 'barQuarter-'+tipo+"-comments")
                    }
                } else {
                    updateQuarterChart(chart, 'barQuarter-'+tipo+"-comments")
                }

                if (opts.selectedDataPoints[0].length === 0) {
                    yearChartEl.classList.remove("chart-quarter-activated")
                    quarterChartEl.classList.remove("active");
                }

            })

            yearChart.addEventListener('updated', function (chart) {
                updateQuarterChart(chart, 'barQuarter-'+tipo+"-comments")
            })


            document.querySelector("#app-"+tipo+"-comments").addEventListener("change", function (e) {
                yearChart.updateSeries([{
                        data: makeData()
                    }])
            })

        },
        error: function () {
            console.log("Errore getBlogViews");
        }
    });
}


/* Grafico per le statistiche delle valutazioni */
function initRateChart(tipo) {


    var keyRate = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueRate = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var keyArtRate = []; /* Valore chiavi hashmap visualizzazioni ultime 4 settimane */
    var valueArtRate = []; /* Valore valori hashmap visualizzazioni ultime 4 settimane */
    var arrayData = [], dataYearSeries = [], quart = [];
    
    Apex = {
        chart: {
            toolbar: {
                show: true
            }
        },
        tooltip: {
            shared: true,
            followCursor: true
        }
    };
    var colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3'];

    
    $.ajax({
        type: "GET",
        url: "/console/getCatRate",
        data: {tipo: tipo},
        dataType: "json",
        success: function (response) {
            var graphDataCat = jQuery.parseJSON(JSON.stringify(response));
            var c = 0;

            for (var keyCat in graphDataCat) {
                keyRate.push("" + keyCat);
                valueRate.push(graphDataCat[keyCat]);
            }
            var r = null;
            for (var c = 0; c < valueRate.length; c++) {
                quart = [];
                keyArtRate = [];
                valueArtRate = [];
                var tmp = null;
                r = function () {
                    $.ajax({
                        async: false,
                        type: "GET",
                        url: "/console/getArtRate",
                        data: {categoria: keyRate[c], tipo: tipo},
                        dataType: "json",
                        success: function (response) {
                            tmp = response;
                        },
                        error: function () {
                            console.log("Errore getArtRate");
                        }
                    });
                    return tmp;
                }();
                
                var graphDataArt = jQuery.parseJSON(JSON.stringify(r));
                for (var keyArt in graphDataArt) {
                    keyArtRate.push("" + keyArt);
                    valueArtRate.push(graphDataArt[keyArt]);
                }

                for (var d = 0; d < valueArtRate.length; d++) {
                    quart[d] = {
                        x: keyArtRate[d],
                        y: valueArtRate[d]
                    };
                }


                arrayData[c] = {
                    y: valueRate[c], /* Valore (Anno) medio della categoria */
                    quarters: quart
                };
            }

            var l = valueRate.length / 7;
            var arrotondato = Math.round(l);
            for (var i = 0; i < arrotondato - 1; i++) {
                colors.push('#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#00D9E9', '#FF66C3');
            }

            function makeData() {
                for (var c = 0; c < valueRate.length; c++) {
                    dataYearSeries[c] = {
                        x: keyRate[c],
                        y: arrayData[c].y,
                        color: colors[c],
                        quarters: arrayData[c].quarters
                    };
                }
                return dataYearSeries;
            }

            var optionsYear = {
                chart: {
                    id: 'barYear',
                    height: 400,
                    width: '100%',
                    type: 'bar',
                },
                plotOptions: {
                    bar: {
                        distributed: true,
                        horizontal: true,
                        barHeight: '75%',
                        dataLabels: {
                            position: 'bottom'
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    textAnchor: 'start',
                    style: {
                        colors: ['#fff']
                    },
                    formatter: function (val, opt) {
                        return opt.w.globals.labels[opt.dataPointIndex]
                    },
                    offsetX: 0,
                    dropShadow: {
                        enabled: true
                    }
                },

                colors: colors,
                series: [{
                        data: makeData()
                    }],
                states: {
                    normal: {
                        filter: {
                            type: 'desaturate'
                        }
                    },
                    active: {
                        allowMultipleDataPointsSelection: false,
                        filter: {
                            type: 'darken',
                            value: 1
                        }
                    }
                },
                tooltip: {
                    x: {
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                },
                title: {
                    text: 'Valutazione per categoria',
                    offsetX: 15
                },
                subtitle: {
                    text: '(Clicca sulle barre per vedere e confrontare i dettagli)',
                    offsetX: 15
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                }
            };

            var yearChart = new ApexCharts(
                    document.querySelector("#chart-year-"+tipo+"-rate"),
                    optionsYear
                    );

            yearChart.render();

            function updateQuarterChart(sourceChart, destChartIDToUpdate) {
                var series = [];
                var seriesIndex = 0;
                var colors = []

                if (sourceChart.w.globals.selectedDataPoints[0]) {
                    var selectedPoints = sourceChart.w.globals.selectedDataPoints;
                    for (var i = 0; i < selectedPoints[seriesIndex].length; i++) {
                        var selectedIndex = selectedPoints[seriesIndex][i];
                        var yearSeries = sourceChart.w.config.series[seriesIndex];
                        series.push({
                            name: yearSeries.data[selectedIndex].x,
                            data: yearSeries.data[selectedIndex].quarters
                        })
                        colors.push(yearSeries.data[selectedIndex].color)
                    }

                    if (series.length === 0)
                        series = [{
                                data: []
                            }]

                    return ApexCharts.exec(destChartIDToUpdate, 'updateOptions', {
                        series: series,
                        colors: colors,
                        fill: {
                            colors: colors
                        }
                    })

                }

            }


            var optionsQuarters = {
                chart: {
                    id: 'barQuarter-'+tipo+"-rate",
                    height: 400,
                    width: '100%',
                    type: 'bar',
                    stacked: true
                },
                plotOptions: {
                    bar: {
                        columnWidth: '50%',
                        horizontal: false
                    }
                },
                series: [{
                        data: []
                    }],
                legend: {
                    show: true
                },
                grid: {
                    yaxis: {
                        lines: {
                            show: false,
                        }
                    },
                    xaxis: {
                        lines: {
                            show: true,
                        }
                    }
                },
                yaxis: {
                    labels: {
                        show: false
                    }
                },
                title: {
                    text: 'Valutazione per articolo',
                    offsetX: 10
                },
                tooltip: {
                    x: {
                        /*formatter: function (val, opts) {
                            return opts.w.globals.seriesNames[opts.seriesIndex]
                        } */
                        show: false
                    },
                    y: {
                        title: {
                            formatter: function (val, opts) {
                                return opts.w.globals.labels[opts.dataPointIndex]
                            }
                        }
                    }
                }
            };

            var chartQuarters = new ApexCharts(
                    document.querySelector("#chart-quarter-"+tipo+"-rate"),
                    optionsQuarters
                    );
            chartQuarters.render();

            yearChart.addEventListener('dataPointSelection', function (e, chart, opts) {
                var quarterChartEl = document.querySelector("#chart-quarter-"+tipo+"-rate");
                var yearChartEl = document.querySelector("#chart-year-"+tipo+"-rate");

                if (opts.selectedDataPoints[0].length === 1) {
                    if (quarterChartEl.classList.contains("active")) {
                        updateQuarterChart(chart, 'barQuarter-'+tipo+"-rate")
                    } else {
                        yearChartEl.classList.add("chart-quarter-activated")
                        quarterChartEl.classList.add("active");
                        updateQuarterChart(chart, 'barQuarter-'+tipo+"-rate")
                    }
                } else {
                    updateQuarterChart(chart, 'barQuarter-'+tipo+"-rate")
                }

                if (opts.selectedDataPoints[0].length === 0) {
                    yearChartEl.classList.remove("chart-quarter-activated")
                    quarterChartEl.classList.remove("active");
                }

            })

            yearChart.addEventListener('updated', function (chart) {
                updateQuarterChart(chart, 'barQuarter-'+tipo+"-rate")
            })


            document.querySelector("#app-"+tipo+"-rate").addEventListener("change", function (e) {
                yearChart.updateSeries([{
                        data: makeData()
                    }])
            })

        },
        error: function () {
            console.log("Errore getBlogRate");
        }
    });
}
