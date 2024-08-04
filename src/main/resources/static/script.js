const url = 'http://localhost:8080/api/admin';



function getRoles(formRole) {
    return Array.from(formRole)
        .filter(option => option.selected)
        .map(option => option.value)
        .map(value => {
            return value === 'ROLE_ADMIN' ? {id: 1, name: 'ROLE_ADMIN'} : {id: 2, name: 'ROLE_USER'};
        })
}

const allUsers = (user) => {
    let output = '';
    user.forEach(el => {
        output += `
                <tr id="${el.id}">
                   <td>${el.id}</td>
                   <td>${el.username}</td>
                   <td>${el.email}</td>
                   <td>${el.yearOfBirth}</td>
                   <td>${el.nameRole}</td>             
                   <td><a type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal"
                            onclick="editUserForm(${el.id})" id="edit-user">Edit</a></td>
                   <td><a type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" 
                            onclick="deleteUserForm(${el.id})" id="delete-user">Delete</a></td>
                </tr>
                `;
    });
    document.getElementById("data").innerHTML += output;
}

if (getCookie('Admin') === 'true') {
    fetch(url)
        .then(response => response.json())
        .then(data => allUsers(data))
}
fetch(url + '/' + getCookie('UserId'))
    .then(response => response.json())
    .then(data => {
        document.getElementById("nameCurrentUser").innerHTML = `<span>${data.username}</span>`;
        document.getElementById("roleCurrentUser").innerHTML = `<span>${data.nameRole}</span>`;
        let current = '';
        current = `
                <tr>
                   <td>${data.id}</td>
                   <td>${data.username}</td>
                   <td>${data.email}</td>
                   <td>${data.yearOfBirth}</td>
                   <td>${data.nameRole}</td>             
                `;
        document.getElementById("currentUser").innerHTML = current;
    })

function addNewUser(method) {
    let form = document.getElementById("formAdd");
    event.preventDefault();
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: form.username.value,
            password: form.password.value,
            email: form.email.value,
            yearOfBirth: form.yearOfBirth.value,
            role: getRoles(form.role)
        })
    })
        .then(response => response.json())
        .then(data => {
            const array = [];
            array.push(data);
            allUsers(array)
        })
    document.getElementById("Users-table-tab").click();
}

function editUser(method) {
    let editRow = '';
    let form = document.getElementById("editUser");
    event.preventDefault();
    fetch((url), {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: form.editId.value,
            username: form.editUsername.value,
            password: form.editPassword.value,
            email: form.editEmail.value,
            yearOfBirth: form.editYearOfBirth.value,
            role: getRoles(form.editRole)
        })
    })
        .then(response => response.json())
        .then(data => {
            editRow = `
                <tr id="${data.id}">
                   <td>${data.id}</td>
                   <td>${data.username}</td>
                   <td>${data.email}</td>
                   <td>${data.yearOfBirth}</td>
                   <td>${data.nameRole}</td>
                   <td><a type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal"
                            onclick="editUserForm(${data.id})" id="edit-user">Edit</a></td>
                   <td><a type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"
                            onclick="deleteUserForm(${data.id})" id="delete-user">Delete</a></td>
                </tr>
            `;
            document.getElementById(data.id).innerHTML = editRow;
        })
    document.getElementById("editModal").click();
}

function editUserForm(userId) {
    let select = document.getElementById("editRole");
    let form = document.getElementById("editUser");
    form.reset();
    fetch((url + '/' + userId),
    )
        .then(response => response.json())
        .then(el => {
            form.editId.value = el.id;
            form.editUsername.value = el.username;
            form.editPassword.value = el.password;
            form.editYearOfBirth.value = el.yearOfBirth;
            form.editEmail.value = el.email;


            let arr = el.role.map(value => {
                return value.id === 1 ? 'ADMIN' : 'USER';
            });
            for (let key in arr) {
                select.options[key].selected = true;
            }
        });
}

function deleteUser(method) {
    let form = document.getElementById("deleteUser");
    event.preventDefault();
    fetch((url + '/' + form.deleteId.value), {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: form.deleteId.value,
            username: form.deleteUsername.value,
            password: form.deletePassword.value,
            email: form.deleteEmail.value,
            yearOfBirth: form.deleteYearOfBirth.value,
            role: getRoles(form.deleteRole)
        })
    })
        .then(response => response.status)
    document.getElementById(form.deleteId.value).remove();
    document.getElementById("deleteModal").click();
}

function deleteUserForm(userId) {
    let optionDel = '';
    let form = document.getElementById('deleteUser');
    fetch(url + '/' + userId)
        .then(response => response.json())
        .then(el => {
            form.deleteId.value = el.id;
            form.deleteUsername.value = el.username;
            form.deletePassword.value = el.password;
            form.deleteEmail.value = el.email;
            form.deleteYearOfBirth.value = el.yearOfBirth;



            let arr = el.role.map(value => {
                return value.id === 1 ? 'ADMIN' : 'USER';
            });
            for (let key in arr) {
                let role = arr[key];
                optionDel += "<option value='" + role + "'>" + role + "</option>";
            }
            document.getElementById("deleteRole").innerHTML = optionDel;
        });
}






function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}