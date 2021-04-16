package com.jdirene.transaction.mock.generator

import kotlin.random.Random

class TextGenerator {

    private val firstLetterPossibilities = "abcdefghijlmnopqrstuvxz"
    private val nextLetterPossibilitiesGraph = mapOf(
        'a' to "bcdefghijlmnopqrstuvxz",
        'b' to "aeioulr",
        'c' to "aeiouhlr",
        'd' to "aeioulr",
        'e' to "abcdfghijlmnopqrstuvxz",
        'f' to "aeioulr",
        'g' to "aeioulr",
        'h' to "aeiou",
        'i' to "abcdefghjlmnopqrstuvxz",
        'j' to "aeiou",
        'l' to "aeiouh",
        'm' to "aeiou", // TODO verificar se vale a pena uma logica para incluir m antes de P e B
        'n' to "aeiou",
        'o' to "abcdefghijlmnpqrstuvxz",
        'p' to "aeioulr",
        'q' to "aeio",
        'r' to "aeiou",
        's' to "aeiou",
        't' to "aeioulr",
        'u' to "abcdefghijlmnopqrstvxz",
        'v' to "aeiou",
        'x' to "aeiou",
        'z' to "aeiou"
    )
    private val lastLetterPossibilities = "aeioulmrsz"
    private val vowels = "aeiou"

    private val minWordLength = 2
    private val maxWordLength = 10
    private val maxWordCount = 10

    //TODO Garantir tamanho minimo do texto
    fun generateText(seed: Int, minLength: Int, maxLength: Int): String {
        var text = ""
        val random = Random(seed)
        val wordCount = random.nextInt(1, maxWordCount)

        for (i in 1..wordCount) {
            val remainingChars = maxLength - text.length
            if (remainingChars < minWordLength) {
                break
            }
            val maxWordLength = random.nextInt(minWordLength, maxWordLength)
            text += generateWord(random, maxWordLength) + " "
        }

        return text.trim().capitalize()
    }

    private fun generateWord(random: Random, maxLength: Int): String {
        var word = getFirstLetter(random).toString()
        for (i in 2..maxLength) {
            word += getNextLetter(word, maxLength, random)
        }
        return word
    }

    private fun getFirstLetter(random: Random): Char {
        return firstLetterPossibilities[random.nextInt(0, firstLetterPossibilities.length)]
    }

    private fun getNextLetter(word: String, wordMaxLength: Int, random: Random): String {
        val lastLetter = word.last()
        val letterIndex = word.length
        // TODO Letra Q não pode ser a pnultima

        val possibleLetters = if (letterIndex == wordMaxLength && vowels.contains(lastLetter))
            lastLetterPossibilities
        else
            nextLetterPossibilitiesGraph[lastLetter]!!

        val nextRandomIndex = random.nextInt(0, possibleLetters.length)

        val nextLetter: Char = possibleLetters[nextRandomIndex]

        return if (lastLetter == 'q')
            "u$nextLetter"
        else
            nextLetter.toString()
    }
}