function convert(type) {
    const file = document.getElementById("fileInput").files[0];
    if (!file) {
        alert("Choisis un fichier !");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    fetch(`/api/convert/${type}`, {
        method: "POST",
        body: formData
    })
    .then(res => res.blob())
    .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "result";
        a.click();
    });
}
