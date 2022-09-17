<script>
    export let value
</script>

<select bind:value>
    <slot></slot>
</select>

<style>
    select {
        /*align-self: start;*/

        font-size: medium;
        padding: 3px;
        width: 100%;
        border: 0;
    }
</style>