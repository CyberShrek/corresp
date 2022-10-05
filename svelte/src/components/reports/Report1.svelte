<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {httpClient} from "../../web/httpClient"
    import {declineNumeral, filtrate, scrollInto} from "../../utils"
    import {fly} from "svelte/transition"
    import PopUp from "../common/PopUp.svelte"
    import Table from "./table/Table.svelte"
    import Row from "./table/Row.svelte"

    export let inputParams

    let rows = [],
        filter,
        report

    $: promise = inputParams &&
        httpClient.createReport1ByParams(inputParams)
            .then(res => {
                rows = res
                scrollInto(report)
            })

    $: total = rows.find(row => row.fromTo = "TOTAL")

</script>

<ReportTemplate bind:report title="Список корреспонденций пассажиропотоков">
    {#await promise}
        <PopUp text="Загрузка отчёта" type="loading"/>
    {:then _}
        {#if rows.length === 0}
            Корресподенции не найдены 😕
        {:else}
        <labels transition:fly={{y: 100}}>
            <SquareLabel name={"Пассажир" + declineNumeral(total.passengers.amount, ["", "а", "ов"])}
                         value={total.passengers.amount} img="img/passenger.png" background="#e2f0d9"/>

            <SquareLabel name={`Тысяч${declineNumeral(total.income.amount, ["а", "и", ""])} рублей`}
                         value={total.income.amount} img="img/wallet.png"    background="#dae3f3"/>

            <SquareLabel name={`Рубл${declineNumeral(total.incomeRate.amount, ["ь", "я", "ей"])} с пассажира`}
                         value={total.incomeRate.amount} img="img/ruble.png"     background="#fbe5d6"/>

            <SquareLabel name={"Пассажиро-километр" + declineNumeral(total.passengerTurnover.amount, ["", "а", "ов"])}
                         value={total.passengerTurnover.amount} img="img/distance.png"  background="#fff2cc"/>
        </labels>
        <Table>
            <th slot="before-0" rowspan="2"></th>
            <th slot="before-1" rowspan="2">
                <input type="text"
                       bind:value={filter}
                       on:click={(event) => scrollInto(event.target.closest("table"))}
                       placeholder="Корресподенция">
            </th>
            <th slot="after-0"  rowspan="2">Количество поездов, ед.</th>
            <tbody slot="body">
            {#each rows as row (row.fromTo)}
                {#if row.fromTo && row.fromTo !== "TOTAL" && filtrate(row.fromTo, filter)}
                    <Row row={row}>
                        <td slot="before-0">
                            <label><input type="checkbox"></label></td>
                        <td slot="before-1">{row.fromTo}</td>
                        <td slot="after-0">
                            <label class="link" title="Показать отчёт по поездам"> {row.trainsCount}</label>
                        </td>
                    </Row>
                {/if}
            {/each}
            </tbody>
        </Table>
        {/if}
    {:catch error}
        Ошибка загрузки 🤬
    {/await}
</ReportTemplate>

<style>
    labels {
        display: flex;
        justify-content: space-around;
        padding: 10px;
    }
    input[type=checkbox]{transform: scale(1.2)}

    .link {
        cursor: pointer;
        font-weight: bold;
        color: deepskyblue;
        text-decoration: underline;
    }
</style>