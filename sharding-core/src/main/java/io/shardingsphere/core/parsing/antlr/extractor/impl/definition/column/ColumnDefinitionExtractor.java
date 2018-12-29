/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor.impl.definition.column;

import com.google.common.base.Optional;
import io.shardingsphere.core.parsing.antlr.extractor.OptionalSQLSegmentExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.util.ExtractorUtils;
import io.shardingsphere.core.parsing.antlr.extractor.util.RuleName;
import io.shardingsphere.core.parsing.antlr.sql.segment.definition.column.ColumnDefinitionSegment;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Column definition extractor.
 * 
 * @author duhongjun
 * @author zhangliang
 */
public final class ColumnDefinitionExtractor implements OptionalSQLSegmentExtractor {
    
    @Override
    public Optional<ColumnDefinitionSegment> extract(final ParserRuleContext ancestorNode) {
        Optional<ParserRuleContext> columnNameNode = ExtractorUtils.findFirstChildNode(ancestorNode, RuleName.COLUMN_NAME);
        if (!columnNameNode.isPresent()) {
            return Optional.absent();
        }
        Optional<ParserRuleContext> dataTypeNode = ExtractorUtils.findFirstChildNode(ancestorNode, RuleName.DATA_TYPE);
        Optional<String> dataTypeText = dataTypeNode.isPresent() ? Optional.of(dataTypeNode.get().getChild(0).getText()) : Optional.<String>absent();
        boolean isPrimaryKey = ExtractorUtils.findFirstChildNode(ancestorNode, RuleName.PRIMARY_KEY).isPresent();
        return Optional.of(new ColumnDefinitionSegment(columnNameNode.get().getText(), dataTypeText.orNull(), isPrimaryKey));
    }
}
