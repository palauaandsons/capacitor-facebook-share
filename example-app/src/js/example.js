import { FacebookShare } from 'capacitor-facebook-share';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    FacebookShare.echo({ value: inputValue })
}
