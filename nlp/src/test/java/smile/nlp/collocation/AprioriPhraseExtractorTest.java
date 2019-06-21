/*******************************************************************************
 * Copyright (c) 2010-2019 Haifeng Li
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/

package smile.nlp.collocation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import smile.nlp.NGram;
import smile.nlp.stemmer.PorterStemmer;
import smile.nlp.tokenizer.SimpleParagraphSplitter;
import smile.nlp.tokenizer.SimpleSentenceSplitter;
import smile.nlp.tokenizer.SimpleTokenizer;

/**
 *
 * @author Haifeng Li
 */
public class AprioriPhraseExtractorTest {

    public AprioriPhraseExtractorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of extract method, of class AprioriPhraseExtractorTest.
     */
    @Test
    public void testExtract() throws IOException {
        System.out.println("extract");
        Scanner scanner = new Scanner(smile.util.Paths.getTestData("text/turing.txt"));
        String text = scanner.useDelimiter("\\Z").next();
        scanner.close();
        
        PorterStemmer stemmer = new PorterStemmer();
        SimpleTokenizer tokenizer = new SimpleTokenizer();
        ArrayList<String[]> sentences = new ArrayList<>();
        for (String paragraph : SimpleParagraphSplitter.getInstance().split(text)) {
            for (String s : SimpleSentenceSplitter.getInstance().split(paragraph)) {
                String[] sentence = tokenizer.split(s);
                for (int i = 0; i < sentence.length; i++) {
                    sentence[i] = stemmer.stripPluralParticiple(sentence[i]).toLowerCase();
                }
                sentences.add(sentence);
            }
        }

        AprioriPhraseExtractor instance = new AprioriPhraseExtractor();
        ArrayList<ArrayList<NGram>> result = instance.extract(sentences, 4, 4);

        assertEquals(5, result.size());
        for (ArrayList<NGram> ngrams : result) {
            for (NGram ngram : ngrams) {
                System.out.print(ngram);
            }
            System.out.println();
        }
    }
}