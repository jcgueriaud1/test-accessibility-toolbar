// import VaadinDevTools from "vaadin-dev-tools/vaadin-dev-tools";
// no typescript definition ?

import "./accessibility-devtools";

import {html} from "lit";

const devtools = document.getElementsByTagName("vaadin-dev-tools");

if (devtools.length == 1) {
   // const vaadinDevTools = (devtools[0] as VaadinDevTools);
    const vaadinDevTools = devtools[0] as any;

    if ((window as any).Vaadin.Flow) {
        vaadinDevTools.tabs.push({ id: 'accessibility', title: 'Accessibility', render: () => renderCode(vaadinDevTools.frontendConnection) });
    }

} else {
    console.error("Dev tool not found // to debug and should make an error in PRODUCTION")
}


// frontendConnection is a Connection, see connection.ts in devtools
function renderCode(frontendConnection: any) {
    return html`<div class="info-tray"><accessibility-devtools .frontendConnection=${frontendConnection}></accessibility-devtools></div>`;
}