let formulario = document.getElementById("formulario");
const url = "http://localhost:8080/WSRestPinkArt";
window.addEventListener("DOMContentLoaded", async () => {});
formulario.addEventListener("submit", async (esc) => {
    esc.preventDefault();

    let coleccion = document.getElementById("colection").value;
    let nickname = document.getElementById("nickname").value;
    let price = document.getElementById("price").value;
    let name = document.getElementById("name").value;
    let photo = document.getElementById("photo").value;

    console.log(coleccion, nickname, price, name, photo);

    let resp = await fetch(url, {
        method: "POST",
        body: JSON.stringify({
            colection: coleccion,
            nickname: nickname,
            price: price+" FCoins",
            name: name,
            photo: photo,
            likes: 0,
        }),
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
        },
    });

    let data = resp.json();
    console.log(data);
    alert("Obra agregada exitosamente");
});
