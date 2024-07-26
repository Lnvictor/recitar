function selectBoxOnChanges(){
    var year = document.getElementById('inputGroupDateAno').value;
    var month = document.getElementById('inputGroupDateMes').value;
    window.location = `/web/ccbrec?year=${year}&month=${month}`;
}

function selectBoxOnChangesMeetings(){
    var year = document.getElementById('inputGroupDateAno').value;
    window.location = `/web/meetings?year=${year}`;
}

function selectBoxOnChangesRecitativos(){
    var year = document.getElementById('inputGroupDateAno').value;
    var month = document.getElementById('inputGroupDateMes').value;
    var set = document.getElementById('inputGroupSet').value;
    var side = document.getElementById('inputGroupLado').value;

    side = side === "irmaos" ? "MAN" : "WOMAN";

    window.location = `/web/recitativos?year=${year}&month=${month}&order=${set}&side=${side}`
}

function toggleAddCountButton(){
    var form = document.getElementById("add-count-form");
    form.style.display = form.style.display === "none" ? "" : "none";
}

function toggleAddRecitativoButton(){
    var form = document.getElementById("add-rec-form");
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



function editRec(){
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
        book: elements[1].textContent,
        chapter: elements[2].textContent,
        firstVerse: elements[3].textContent,
        side: elements[4].textContent,
        order: elements[5].textContent,
    }

    var side = obj.side === "Meninos" ? "MAN" : "WOMEN";

    document.getElementById("date").value = obj.date;
    document.getElementById("order").value = obj.order;
    document.getElementById("side").value = side;
    document.getElementById("book").value = obj.book;
    document.getElementById("chapter").value = obj.chapter;
    document.getElementById("first-verse").value = obj.firstVerse;

    document.getElementById("add-rec-form").submit();
}

function newMeetingAddParticipant(){
    var selectBox = document.getElementById("auxiliaresParticipants");
    var selectedAuxiliarIndex = selectBox.selectedIndex;

    var textArea = document.getElementById("participants");
    if (textArea.innerHTML.length > 0){
        textArea.innerHTML += "\n";
    }
    textArea.innerHTML += selectBox[selectedAuxiliarIndex].innerHTML;
}

function handleRoleChangeRequest(username, currentRole, operation){
    var form = document.getElementById("form-change-role-user");
    var usernameInput = document.getElementById("role-form-username");
    var operationInput = document.getElementById("role-form-operation");
    var roleInput = document.getElementById("role-form-role");
    var button = document.getElementById("role-form-submit");


    usernameInput.value = username;
    operationInput.value = operation;
    roleInput.value = currentRole;

    form.submit();
}

function toggleAddUser(){
    var form = document.getElementById("form-add-user")
    form.style.display = form.style.display === "none" ? "" : "none";
}