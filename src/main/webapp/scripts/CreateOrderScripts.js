var daysCount = document.getElementById('days_count');
var price     = document.getElementById('price_per_day');
var sumElement = document.getElementById('sum');

function getSum(){
    var sum = parseFloat(price.innerHTML)*parseInt(daysCount.value);
    sum = sum*100;
    sum = Math.round(sum);
    sumElement.value = sum/100;
}