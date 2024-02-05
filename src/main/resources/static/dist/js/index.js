const activePage =  window.location.pathname;
const btnLinks = document.querySelectorAll(".sectionBtn").forEach(link => {
    if(link.href.includes(`${activePage}`)){
        link.classList.add('active');
    }
})