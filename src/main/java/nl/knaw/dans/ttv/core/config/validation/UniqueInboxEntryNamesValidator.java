/*
 * Copyright (C) 2021 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.ttv.core.config.validation;

import nl.knaw.dans.ttv.core.config.CollectConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniqueInboxEntryNamesValidator implements ConstraintValidator<UniqueInboxEntryNames, List<CollectConfiguration.InboxEntry>> {

    @Override
    public boolean isValid(List<CollectConfiguration.InboxEntry> inboxEntries, ConstraintValidatorContext constraintValidatorContext) {
        // This groups by the name property and checks if there are more than 1 entries for that.
        // Only unique names are allowed, so duplicates will make it return false
        var inboxNames = inboxEntries.stream()
            .collect(Collectors.groupingBy(CollectConfiguration.InboxEntry::getName))
            .entrySet()
            .stream().filter(e -> e.getValue().size() > 1)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        return inboxNames.size() <= 0;
    }
}