function termsAndConditionsClick() {
    alert("PRIVATLIVSPOLITIK\n" +
        "\n" +
        "Denne privatlivspolitik forklarer, hvordan vi indsamler, bruger og beskytter dine personoplysninger.\n" +
        "\n" +
        "Vi indsamler kun oplysninger, som du selv giver os, herunder navn, e-mailadresse, telefonnummer samt relevante oplysninger i forbindelse med medlemskab, kontakt eller deltagelse i aktiviteter.\n" +
        "\n" +
        "Oplysningerne bruges til at administrere medlemskaber, kommunikere med dig, sende relevant information samt forbedre vores tjenester og aktiviteter.\n" +
        "\n" +
        "Vi opbevarer dine oplysninger sikkert og kun så længe, det er nødvendigt for de formål, de er indsamlet til. Vi træffer passende tekniske og organisatoriske sikkerhedsforanstaltninger for at beskytte dine data.\n" +
        "\n" +
        "Vi videregiver ikke dine oplysninger til tredjeparter, medmindre det er nødvendigt for drift (fx betalingsløsninger), kræves ved lov, eller du har givet samtykke.\n" +
        "\n" +
        "Du har ret til at få indsigt i dine oplysninger, få dem rettet eller slettet, samt til at trække dit samtykke tilbage. Du kan også gøre indsigelse mod behandlingen af dine data.\n" +
        "\n" +
        "Hvis du har spørgsmål til vores behandling af personoplysninger, kan du kontakte os via vores officielle kontaktoplysninger.\n" +
        "\n" +
        "Ved at bruge vores tjenester accepterer du denne privatlivspolitik.\n");
}

function validateTermsAndConditionsCheckbox(event) {
    if (document.querySelector('input[name="terms"]:checked')) {
        document.getElementById('errorMsg').textContent = "";
        return true;
    }

    event.preventDefault(); //stop form from submitting
    document.getElementById('errorMsg').textContent = "Du skal acceptere privatlivspolitikken, før du kan registrere dig"
    return false;
}