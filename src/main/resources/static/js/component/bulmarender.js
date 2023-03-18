bulmarender = {
    build: function () {
        let object = this;

        object.buildModal();
    },

    buildModal: function () {
        let object = this;
        let modalList = document.querySelectorAll(".modal");

        modalList.forEach(modal => {
            // RENDER FUNCTION FOR CLOSE BUTTON
            let closeButton = modal.querySelector(".modal-close");
            if (closeButton) {
                closeButton.onclick = function () {
                    modal.classList.remove("is-active");
                }
            }

            // RENDER FUNCTION FOR BACKGROUND MODAL
            let backgroundModal = modal.querySelector(".modal-background");
            if (backgroundModal) {
                backgroundModal.onclick = function () {
                    modal.classList.remove("is-active");
                }
            }
        })

        console.log("Build Modal completed!");
    },
};