<script>
    import Period from "./fields/Period.svelte"
    import Carrier from "./fields/Carrier.svelte"
</script>

<form>
    <field><Period/></field>
    <field><Carrier/></field>


    <submit><input type="submit" value="Сформировать отчёт"></submit>
</form>

<style>
    form {
        display: flex;

        margin: 5px;
        background-color: whitesmoke;
        border-radius: 5px;
        box-shadow: 0 0 3px gray
    }

    form field {
        display: flex;
        flex-direction: column;
        align-items: center;

        padding: 0 10px;
        border-right: 1px solid lightgray;
    }
</style>