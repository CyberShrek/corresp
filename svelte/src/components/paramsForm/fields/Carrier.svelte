<script>
    import Heading from "../parts/Heading.svelte"
    import {httpClient} from "../../../util/httpClient"
    import Dropdown from "../../common/Dropdown.svelte"
</script>

<Heading text="Перевозчик"/>

{#await httpClient.get("carriers")}
    <Dropdown message="Загружаю список перевозчиков"/>
{:then carriers}
    {#each carriers as carrier}
        <label>
            <input type="radio" name="carriers" value={carrier}> {carrier}
        </label>
    {/each}
{:catch error}
    <Dropdown message="Не удалось загрузить данные" isError="true"/>
{/await}

<style>
    label {
        margin: 0;
        width: 100%;
    }
</style>