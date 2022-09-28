<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte";
</script>

<ReportTemplate title="Список корреспонденций пассажиропотоков">
    <labels>
        <SquareLabel name="Пассажиры"          value="1488" img="img/passenger.png" background="#e2f0d9"/>
        <SquareLabel name="Тысяч рублей"       value="1488" img="img/wallet.png"    background="#dae3f3"/>
        <SquareLabel name="Рублей с пассажира" value="1488" img="img/ruble.png"     background="#fbe5d6"/>
        <SquareLabel name="Пасс-км"            value="1488" img="img/distance.png"  background="#fff2cc"/>
    </labels>
</ReportTemplate>

<style>
    labels {
        display: flex;
        justify-content: space-around;
        padding: 20px;
    }
</style>