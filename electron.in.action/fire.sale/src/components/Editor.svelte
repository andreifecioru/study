<script>
  import MarkdownArea from "./MarkdownArea.svelte";
  import HtmlArea from "./HtmlArea.svelte";

  const marked = require("marked");
  marked.setOptions({
    gfm: true,
    breaks: true,
  });

  let content = "";

  const onMarkdownChanged = event => {
    content = marked(event.detail.content);
  }
</script>

<MarkdownArea on:markdown-changed="{onMarkdownChanged}"/>
<HtmlArea>
{@html content}
</HtmlArea>
