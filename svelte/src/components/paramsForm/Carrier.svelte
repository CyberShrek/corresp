<script>
    import {httpClient} from "../../web/httpClient"
    import Dropdown from "../common/Dropdown.svelte"
    import MultiSelect from "../common/MultiSelect.svelte"

    export let inputDate, selected, isValid

    let carriers = [], selectedCarriers = []

    $: carriersPromise = inputDate ?
        httpClient.getCarriersByDate(inputDate)
            .then(r => carriers = r) : carriers = []

    $: selected = selectedCarriers[0]

    $: isValid = selected !== undefined

</script>

<MultiSelect options={carriers}
             bind:selectedOptions={selectedCarriers}
             placeholder="Перевозчик"
             singleSelect={true}/>

    {#await carriersPromise} <Dropdown text="Загружаю список перевозчиков"/>
    {:catch error}           <Dropdown text="Ошибка загрузки списка перевозчиков" type="error"/>
    {/await}