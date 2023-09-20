let inputElement = document.querySelector(".input-group");
let loading = document.querySelector(".loading_wrap");
let searchBt = document.querySelector(".input-group button")

window.addEventListener("beforeunload", () =>{
   loading.classList.toggle("on");
})

window.addEventListener("unload", () =>{
   loading.classList.toggle("on");
})

$('#videoModal').on('hidden.bs.modal', function (e) {
   // Note: '#video' is the id of the iframe
   $('#video').attr('src', $('#video').attr('src'));
});