<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {httpClient} from "../../web/httpClient"
    import {declineNumeral, filtrate, scrollInto} from "../../utils"
    import {fly} from "svelte/transition"
    import Table from "./table/Table.svelte"
    import Row from "./table/Row.svelte"
    import {createEventDispatcher} from "svelte"

    const dispatch = createEventDispatcher()

    export let inputParams

    let report,
        totalRow,
        selectedRows = [],
        filter,
        filterWidth

    $: totalRow = report && report.rows.find(row => row.fromToNames = "TOTAL")
    $: noRowsSelected = selectedRows.length < 1
</script>

<ReportTemplate title="Список корреспонденций пассажиропотоков"
                inputParams={inputParams}
                bind:report>
    {#if report.rows.length === 1}
        Корресподенции не найдены 😕
    {:else}
    <labels transition:fly={{y: 100}}>
        <SquareLabel name={"Пассажир" + declineNumeral(totalRow.passengers, ["", "а", "ов"])}
                     value={totalRow.passengers}
                     image="img/passenger.png"
                     background="#e2f0d9"/>

        <SquareLabel name={`Тысяч${declineNumeral(totalRow.income, ["а", "и", ""])} рублей`}
                     value={totalRow.income}
                     image="img/wallet.png"
                     background="#dae3f3"/>

        <SquareLabel name={`Рубл${declineNumeral(totalRow.incomeRate, ["ь", "я", "ей"])} с пассажира`}
                     value={totalRow.incomeRate}
                     image="img/ruble.png"
                     background="#fbe5d6"/>

        <SquareLabel name={"Пассажиро-километр" + declineNumeral(totalRow.passengerTurnover, ["", "а", "ов"])}
                     value={totalRow.passengerTurnover}
                     image="img/distance.png"
                     background="#fff2cc"/>
    </labels>
    <Table>
        <th slot="before-0" rowspan="2"></th>
        <th slot="before-1" rowspan="2">
            <buttons-block style="width: {filterWidth}px" class:unavailable={noRowsSelected}>
                <button title="Анализ неравномерности"><img          src="img/graph.png"     alt=""></button>
                <button title="Анализ по типам вагонов"><img         src="img/train_car.png" alt=""></button>
                <button title="Анализ по виду документа"><img        src="img/document.png"  alt=""></button>
                <button title="Анализ по характеристикам места"><img src="img/seat.png"      alt=""></button>
            </buttons-block>

            <label bind:offsetWidth={filterWidth}>
                <input type="text"
                       bind:value={filter}
                       on:click={(event) => scrollInto(event.target.closest("table"))}
                       placeholder="Корресподенции"></label>
        </th>
        <th slot="after-0"  rowspan="2">Количество поездов, ед.</th>

        <tbody slot="body">
        {#each report.rows as row (row.fromToNames)}
            {#if row.fromToNames
                && row.fromToNames !== "TOTAL"
                && filtrate(row.fromToNames, filter)}
                <Row row={row}>
                    <td slot="before-0">
                        <input type="checkbox"
                               value={row}
                               bind:group={selectedRows}>
                        </td>
                    <td slot="before-1">{row.fromToNames}</td>
                    <td slot="after-0">
                        <div class="link"
                             title="Показать отчёт по поездам"
                             on:click={() => dispatch("generateReport2",
                             {
                                 date1: inputParams.date1,
                                 date2: inputParams.date2,
                                 compareWithLastYear: inputParams.compareWithLastYear,
                                 carrierCode: inputParams.carrierCode,
                                 fromCodes: row.fromCode,
                                 toCodes: row.toCode
                             }
                             )}> {row.trainsCount}</div>
                    </td>
                </Row>
            {/if}
        {/each}
        </tbody>
    </Table>
    {/if}
</ReportTemplate>

<div class="void"></div>

<style>
    labels {
        display: flex;
        justify-content: space-around;
        padding: 10px;
    }
    input[type=checkbox]{transform: scale(1.3)}

    .link {
        cursor: pointer;
        font-weight: bold;
        color: deepskyblue;
        text-decoration: underline;
    }

    th buttons-block{
        display: flex;
        justify-content: space-between;
        margin: 0 auto 10px auto;
    }
    th buttons-block button{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 30px;
        width:  30px;
        padding: 0;
        background: white;
    }
    th buttons-block button img{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 80%;
        width:  80%;
    }

    .void {
        height: 100vh;
    }
</style>