# 猴油脚本开发

有一些重复性质的体力劳动，本来想用填表工具来解决，结果找了一圈，也没有找到一个好的工具，最后决定写个油猴脚本来实现。
![Kapture-tampermonkey](../assets/Kapture-tampermonkey.gif)

[文档](https://www.tampermonkey.net/documentation.php#_namespace)

安装Chrome下的 tampermonkey 插件。

```javascript
// ==UserScript==
// @name         Fuck PMS
// @namespace    https://www.cnblogs.com/warrior
// @version      0.1
// @description  try to take over the world!
// @author       warriorg
// @match        http://192.168.10.128:8066/
// @grant        GM_addStyle			
// ==/UserScript==

const actions = [
    { icon: 'ios-add', label: '新增', code: 'add', group: 'default'},
    { icon: 'ios-create-outline', label: '编辑', code: 'edit', group: 'default'},
    { icon: 'ios-trash-outline', label: '删除', code: 'delete', group: 'default'},
    { icon: 'ios-cloud-upload-outline', label: '导入', code: 'import', group: 'default'},
    { icon: 'ios-cloud-download-outline', label: '导出', code: 'export', group: 'default'},
    { icon: 'ios-send', label: '发送内审', code: 'add', code: 'send-audit', group: 'default'},
    { icon: 'ios-send-outline', label: '发送备案', code: 'send-set', group: 'default'},
    { icon: 'ios-share-alt-outline', label: '备案通过', code: 'pass-set', group: 'default'} ];

const options = []
for (let i = 0; i < actions.length; i++) {
    options.push(`<option value="${i}">${actions[i].label}</option>`)
}

const zNode = document.createElement ('div');
zNode.innerHTML = `<select id="action">${options.join('')}</select><button id='myButton'>FUCK</button>`
zNode.setAttribute ('id', 'myContainer');
document.body.appendChild (zNode);
document.getElementById ("myButton").addEventListener ("click", handleFuck, false);

function handleFuck (e) {
    var iframe = document.getElementsByTagName("iframe")[1]
    let contentDocument = iframe.contentWindow.document

    let index = parseInt(document.getElementById("action").value)
    contentDocument.getElementById("MainContent_txtCode").value = actions[index].code
    contentDocument.getElementById("MainContent_txtName").value = actions[index].label
    contentDocument.getElementById("MainContent_txtInco").value = actions[index].icon
    contentDocument.getElementById("MainContent_txtXGroup").value = actions[index].group
    contentDocument.getElementById("MainContent_ucFunctionType1_ddlFunctionType").options[2].selected = 'selected'
    contentDocument.getElementById("Save").click()
}

GM_addStyle (`
    #myContainer {
        position:               absolute;
        top:                    10px;
        right:                  10px;
        font-size:              20px;
        background:             red;
        margin:                 5px;
        opacity:                0.9;
        z-index:                1100;
        padding:                5px 20px;
    }
    #myButton {
        cursor:                 pointer;
        padding: 10px;
    }
    #myContainer p {
        color:                  red;
        background:             white;
    }
`);
```

