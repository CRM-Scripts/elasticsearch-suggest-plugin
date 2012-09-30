package org.elasticsearch.rest.action.suggest.SuggestActionTest;

import java.util.List;

import org.elasticsearch.action.suggest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class TransportSuggestActionTest extends AbstractSuggestTest {

    public TransportSuggestActionTest(int shards, int nodeCount) throws Exception {
        super(shards, nodeCount);
    }

    @Override
    public List<String> getSuggestions(String field, String term, Integer size, Float similarity) throws Exception {
        SuggestRequest request = new SuggestRequest("products");

        request.term(term);
        request.field(field);

        if (size != null) {
            request.size(size);
        }
        if (similarity != null && similarity > 0.0 && similarity < 1.0) {
            request.similarity(similarity);
        }

        SuggestResponse response = node.client().execute(SuggestAction.INSTANCE, request).actionGet();

        return response.suggestions();
    }

    @Override
    public List<String> getSuggestions(String field, String term, Integer size)
            throws Exception {
        return getSuggestions(field, term, size, null);
    }

    @Override
    public void refreshSuggestIndex() throws Exception {
        NodesSuggestRefreshRequest refreshRequest = new NodesSuggestRefreshRequest();
        node.client().execute(SuggestRefreshAction.INSTANCE, refreshRequest).actionGet();
    }

}
