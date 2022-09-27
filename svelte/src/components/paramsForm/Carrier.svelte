<script>
    import {httpClient} from "../../web/httpClient"
    import Dropdown from "../common/Dropdown.svelte"
    import MultiSelect from "../common/MultiSelect.svelte"

    export let inputDate, selected, isValid

    let carriers = [], selectedCarrierNames = []

    $: carriersPromise = inputDate ?
        httpClient.getCarriersByDate(inputDate)
            .then(r => carriers = r) : carriers = selectedCarrierNames = []

    $: selected = selectedCarrierNames.map(name => carriers.find(desired => desired.name === name))[0]

    $: isValid = selected !== undefined

</script>

<MultiSelect options={carriers.map(carrier => carrier.name)}
             placeholder="Перевозчик"
             bind:selectedOptions={selectedCarrierNames}
             singleSelect={true}/>

    {#await carriersPromise} <Dropdown text="Загружаю список перевозчиков"/>
    {:catch error}           <Dropdown text="Ошибка загрузки списка перевозчиков" type="error"/>
    {/await}