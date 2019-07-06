<script>
  import Header from "./Header.svelte"
  import Editor from "./Editor.svelte"

</script>

<style type="text/scss">
  main {
    min-height: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 4em 1fr;
  }

</style>

<main>
  <Header />
  <Editor />
</main>
