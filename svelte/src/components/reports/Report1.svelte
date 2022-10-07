<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {httpClient} from "../../web/httpClient"
    import {declineNumeral, filtrate, scrollInto} from "../../utils"
    import {fly} from "svelte/transition"
    import Table from "./table/Table.svelte"
    import Row from "./table/Row.svelte"
    import {createEventDispatcher} from "svelte"
    import {defineIndexes} from "./table/indexes"
    const dispatch = createEventDispatcher()

    export let inputParams

    let report = {header: undefined, content: undefined},
        inx,
        total

    $: promise = inputParams && httpClient.getReportByParams(1, inputParams)
        .then(res => updateReport(res))

    function updateReport(rep){
        report = rep
        inx    = defineIndexes(report.header)
        total  = report.content.find(row => row[inx.fromToNames] = "TOTAL")
    }

    let filter
</script>

<ReportTemplate loadingPromise={promise} title="Список корреспонденций пассажиропотоков">
    {#if report.content.length === 1}
        Корресподенции не найдены 😕
    {:else}
    <labels transition:fly={{y: 100}}>
        <SquareLabel name={"Пассажир" + declineNumeral(total[inx.passengers], ["", "а", "ов"])}
                     value={total[inx.passengers]}
                     img="img/passenger.png"
                     background="#e2f0d9"/>

        <SquareLabel name={`Тысяч${declineNumeral(total[inx.income], ["а", "и", ""])} рублей`}
                     value={total[inx.income]}
                     img="img/wallet.png"
                     background="#dae3f3"/>

        <SquareLabel name={`Рубл${declineNumeral(total[inx.incomeRate], ["ь", "я", "ей"])} с пассажира`}
                     value={total[inx.incomeRate]}
                     img="img/ruble.png"
                     background="#fbe5d6"/>

        <SquareLabel name={"Пассажиро-километр" + declineNumeral(total[inx.passengerTurnover], ["", "а", "ов"])}
                     value={total[inx.passengerTurnover]}
                     img="img/distance.png"
                     background="#fff2cc"/>
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
        {#each report.content as row (row[inx.fromToNames])}
            {#if row[inx.fromToNames]
                && row[inx.fromToNames] !== "TOTAL"
                && filtrate(row[inx.fromToNames], filter)}
                <Row row={row} indexes={inx}>
                    <td slot="before-0">
                        <label><input type="checkbox"></label></td>
                    <td slot="before-1">{row[inx.fromToNames]}</td>
                    <td slot="after-0">
                        <div class="link"
                             title="Показать отчёт по поездам"
                             on:click={() => dispatch("generateReport2",
                             {
                                 date1: inputParams.date1,
                                 date2: inputParams.date2,
                                 compareWithLastYear: inputParams.compareWithLastYear,
                                 carrierCode: inputParams.carrierCode,
                                 fromCode: row[inx.fromCode],
                                 toCode: row[inx.toCode]
                             }
                             )}> {row[inx.trainsCount]}</div>
                    </td>
                </Row>
            {/if}
        {/each}
        </tbody>
    </Table>
    {/if}
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