<script>
    export let value
</script>

<select bind:value>
    <slot></slot>
</select>

<style>
    select {
        font-size: medium;
        padding: 3px;
        width: 100%;
        background: white;
        border: none;
        border-bottom: var(--border);
    }
</style>