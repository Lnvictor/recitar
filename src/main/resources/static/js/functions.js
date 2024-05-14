function selectBoxOnChanges(){
    var year = document.getElementById('inputGroupDateAno').value;
    var month = document.getElementById('inputGroupDateMes').value;
    window.location = `/web/ccbrec?year=${year}&month=${month}`;
}

function toggleAddCountButton(){
    var form = document.getElementById("add-count-form");
    form.style.display = form.style.display === "none" ? "" : "none";
}

function editCount(){
    var tableRow;

    // Here we see a POG made for the case of user clicks on svg instead of button el
    if (event.srcElement.tagName === 'svg'){
        tableRow = event.srcElement.parentElement.parentElement.parentElement;
    }
    else{
        tableRow = event.srcElement.parentElement.parentElement;
    }

    var elements = tableRow.getElementsByTagName("td");
    var s = elements[0].textContent.split("/");
    var newDate = `${s[2]}-${s[1]}-${s[0]}`

    var obj = {
        date: newDate,
        boys: elements[1].textContent,
        girls: elements[2].textContent,
        youngBoys: elements[3].textContent,
        youngGirls: elements[4].textContent,
        individuals: elements[5].textContent,
    }

    document.getElementById("date").value = obj.date;
    document.getElementById("boys").value = obj.boys;
    document.getElementById("girls").value = obj.girls;
    document.getElementById("youngBoys").value = obj.youngBoys;
    document.getElementById("youngGirls").value = obj.youngGirls;
    document.getElementById("individuals").value = obj.individuals;

    document.getElementById("add-count-form").submit();
}
