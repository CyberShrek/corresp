<!-- Drops a floating div exactly where the <Notation> component was added-->

<script>
    import { onMount } from "svelte"
    import { blur } from "svelte/transition"

    export let text, type, size

    $: background = type==="error"   ? "red"
                  : type==="warning" ? "darkkhaki"
                  : type==="button"  ? "darkseagreen"
                  : "lightskyblue"

    let render, top, left, width

    onMount(() => {
        let nearestElem = render.previousElementSibling
        if (!nearestElem) nearestElem = render.parentElement
        top   = nearestElem.offsetTop + nearestElem.clientHeight + "px"
        left  = nearestElem.offsetLeft - nearestElem.clientLeft + "px"
        width = nearestElem.clientWidth + "px"
    })
</script>

<div bind:this={render}
     style="top:{top}; left:{left}; width:{width}; font-size:{size}">
    {#if type==="button"}
        <input type="button"
               transition:blur
               style="background:{background}"
               value={text}/>
    {:else}
        <p transition:blur
           style="background:{background}">
            {text}
        </p>
    {/if}
</div>


<style>
    div{
        display: flex;
        position:absolute;
        margin: 5px;
    }
    p, input[type=button] {
        text-align: center;
        color: white;
        border-radius: 5px;
        box-shadow: 0 0 3px gray;
    }
</style>