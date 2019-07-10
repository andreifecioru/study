<script>
  import MarkdownArea from "./MarkdownArea.svelte";
  import HtmlArea from "./HtmlArea.svelte";

  const { remote, ipcRenderer } = require("electron");
  const marked = require("marked");
  const currentWindow = remote.getCurrentWindow();

  const filePath = null;
  let originalContent = "";
  let htmlContent = "";
  let markdownContent = "";

  ipcRenderer.on("open-file", (event, fileName, content) => {
    console.log(`Loaded markdown file: ${fileName}`);
    htmlContent = marked(content);
    markdownContent = content;

    // keep track of the file path and it's original content
    // (to detect changes)
    filePath = fileName;
    originalContent = content;

    // update the window title
    let title = "FireSale";
    if (filePath) {
      title = `${filePath} - ${title}`;
    }
    currentWindow.setTitle(title);
  });

  marked.setOptions({
    gfm: true,
    breaks: true
  });

  const onMarkdownChanged = event => {
    htmlContent = marked(event.detail.content);
  };
</script>

<MarkdownArea on:markdown-changed={onMarkdownChanged} content="{markdownContent}" />
<HtmlArea>
  {@html htmlContent}
</HtmlArea>
