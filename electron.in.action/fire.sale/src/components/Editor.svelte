<script>
  import MarkdownArea from "./MarkdownArea.svelte";
  import HtmlArea from "./HtmlArea.svelte";

  const marked = require("marked");

  let htmlContent = "";
  let markdownContent = "";

  const { ipcRenderer } = require("electron");
  ipcRenderer.on("open-file", (event, fileName, content) => {
    console.log(`Loaded markdown file: ${fileName}`);
    htmlContent = marked(content);
    markdownContent = content;
  });

  marked.setOptions({
    gfm: true,
    breaks: true
  });

  const onMarkdownChanged = event => {
    htmlContent = marked(event.detail.content);
  };
</script>

<MarkdownArea on:markdown-changed={onMarkdownChanged} content="{markdownContent}"> </MarkdownArea>
<HtmlArea>
  {@html htmlContent}
</HtmlArea>
