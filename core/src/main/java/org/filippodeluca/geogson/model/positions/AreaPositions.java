/*
 * Copyright 2013 Filippo De Luca - me@filippodeluca.com
 *
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
 */

package org.filippodeluca.geogson.model.positions;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

/**
 * @author Filippo De Luca - me@filippodeluca.com
 */
public class AreaPositions implements Positions {

    private final ImmutableList<LinearPositions> children;

    public AreaPositions(ImmutableList<LinearPositions> children) {
        this.children = children;
    }

    public AreaPositions(Iterable<LinearPositions> children) {
        this(ImmutableList.copyOf(children));
    }

    @Override
    public Positions merge(Positions other) {

        if(other instanceof SinglePosition) {

            throw new IllegalArgumentException("Cannot merge single position and area children");
        } else if(other instanceof LinearPositions) {

            LinearPositions that = (LinearPositions) other;
            return new AreaPositions(ImmutableList.<LinearPositions>builder().addAll(children).add(that).build());
        } else if (other instanceof AreaPositions) {

            AreaPositions that = (AreaPositions) other;
            return new MultiDimensionalPositions(ImmutableList.of(this, that));
        } else {

            return other.merge(this);
        }
    }

    @Override
    public Iterable<LinearPositions> children() {
        return children;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(children);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AreaPositions other = (AreaPositions) obj;
        return Objects.equal(this.children, other.children);
    }
}
