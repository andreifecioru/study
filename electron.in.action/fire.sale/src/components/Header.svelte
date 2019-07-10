<script>
  const { remote } = require("electron");
  const mainProcess = remote.require("./main.js");

  const currentWindow = remote.getCurrentWindow();

  const onOpenFile = event => {
    mainProcess.loadMarkdownFile(currentWindow);
  }

  const onNewFile = event => {
    mainProcess.createWindow();
  }
</script>


<style type="text/scss">
  header {
    grid-column: span 2;
    grid-row: span 1;
    border-bottom: 1px solid grey;
  }

  ul {
    list-style: none;
    display: flex;
    justify-content: center;
  }

  ul > li {
    display: inline-block;
  }

  ul > li + li {
    margin-left: .5em;
  }

  .button {
    background: teal;
    color: white;
    padding: .5em 1em;
    border-radius: .5em;
    box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.26);
    cursor: pointer;
  }
</style>

<header>
  <nav>
    <ul>
      <li class="new button" on:click="{onNewFile}">New</li>
      <li class="load button" on:click="{onOpenFile}">Load</li>
      <li class="save button">Save</li>
      <li class="export button">Export</li>
      <li class="revert button">Revert</li>
    </ul>
  </nav>
</header>
