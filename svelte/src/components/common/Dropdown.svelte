<!-- Drops a floating notation exactly where the <Notation> component was added-->

<script>
    import { onMount } from "svelte"
    import { blur, slide } from "svelte/transition"

    export let text, type, size

    $: background = type==="error"   ? "red"
                  : type==="warning" ? "darkkhaki"
                  : "lightskyblue"

    let render, top, width

    onMount(() => {
        let nearestElem = render.previousElementSibling,
            parentElem = render.parentElement
        if (!nearestElem) nearestElem = parentElem
        top   = nearestElem.offsetTop + nearestElem.clientHeight + "px"
        width = parentElem.clientWidth - 10 + "px"
    })
</script>

    {#if type==="button"}
        <input type="button"
               bind:this={render}
               transition:slide
               style="top:{top}; width:{width}; font-size:{size}"
               value={text}/>
    {:else}
        <p bind:this={render}
           transition:blur
           style="background:{background}; color: white; top:{top}; width:{width}; font-size:{size}">
            {text}
        </p>
    {/if}


<style>
    p, input[type=button] {
        position:absolute;
        margin: 5px;
        text-align: center;
        border-radius: 5px;
        box-shadow: 0 0 3px gray;
    }
</style>