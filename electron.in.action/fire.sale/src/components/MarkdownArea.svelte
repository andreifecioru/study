<script>
  import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();

  const onMarkdownChanged = event => {
    dispatch('markdown-changed', {content: event.target.value});
  };
</script>

<style type="text/scss">
  @import "./scss/base.scss";

  section.markdown-area {
    overflow: hidden;
    font-family: monospace;
    margin: 0;
    padding: 0;
  }

  textarea {
    resize: none;
    min-width: 100%;
    min-height: 100%;
    margin: 0;
    padding: 0;
    border: 0;

    background: $light-grey;
    overflow: auto;
  }
</style>

<section class="markdown-area">
  <textarea name="markdown-text-area" id="markdown-text-area" on:input="{onMarkdownChanged}"></textarea>
</section>
