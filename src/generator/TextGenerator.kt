package com.jdirene.transaction.mock.generator

import kotlin.random.Random

class TextGenerator {

    //    private val firstLetterPossibilities = "abcdefghijlmnopqrstuvxz"
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
        'm' to "aeiou",
        'n' to "aeiou",
        'o' to "abcdefghijlmnpqrstuvxz",
        'p' to "aeioulr",
        'r' to "aeiou",
        's' to "aeiou",
        't' to "aeioulr",
        'u' to "abcdefghijlmnopqrstvxz",
        'v' to "aeiou",
        'x' to "aeiou",
        'z' to "aeiou",
        'q' to "aeio"
    )
    private val lastLetterPossibilities = "aeioulmrsz"
    private val vowels = "aeiou"

    private val minWordLength = 2
    private val maxWordLength = 10
    private val maxWordCount = 10

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
        text = text.trim()

        if(text.length < minLength){
            val missingChars = minLength - text.length
            if(missingChars == 1){
                text+= '.'
            } else {
                text += generateWord(random, missingChars)
            }
        }

        return text.capitalize()
    }

    private fun generateWord(random: Random, maxLength: Int): String {
        var word = getFirstLetter(random).toString()
        for (i in 2..maxLength) {
            word += getNextLetter(word, maxLength, random)
        }
        return word
    }

    private fun getFirstLetter(random: Random): Char {
        val firstLetterPossibilities = nextLetterPossibilitiesGraph.keys.toList()
        return firstLetterPossibilities[random.nextInt(0, firstLetterPossibilities.size - 1)]
    }

    private fun getNextLetter(word: String, wordMaxLength: Int, random: Random): String {
        val beforeLetter = word.last()
        val letterIndex = word.length
        val isLastLetter = letterIndex == wordMaxLength
        val isPenultimateLetter = letterIndex == (wordMaxLength - 1)

        val possibleLetters = if (isLastLetter && vowels.contains(beforeLetter))
            lastLetterPossibilities
        else
            nextLetterPossibilitiesGraph[beforeLetter]!!

        /**
         * Avoid pnultimate letter is q to don't exceed the maximum length
         */
        val maxNextLetterIndex = if(isPenultimateLetter){
            possibleLetters.length - 2
        } else {
            possibleLetters.length - 1
        }
        val nextRandomIndex = random.nextInt(0, maxNextLetterIndex)

        val nextLetter: Char = possibleLetters[nextRandomIndex]

        return if (beforeLetter == 'q')
            "u$nextLetter"
        else
            nextLetter.toString()
    }
}