/*
 * Copyright 2017 Kafdrop contributors.
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
 *
 *
 */

package kafdrop.model;

import java.util.Objects;

public final class AclVO implements Comparable<AclVO>{

    private String name;
    private String dn;
    private String topic;
    private String op;

    public AclVO() {

    }

    public AclVO(String dn, String name, String op, String topic) {
        this.name= name;
        this.dn= dn;
        this.topic= topic;
        this.op= op;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getDn() {
        return dn;
    }

    public String getOp() {
        return op;
    }

  @Override
  public int compareTo(AclVO that) {
    return this.name.compareTo(that.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AclVO aclVO = (AclVO) o;
    return name.equals(aclVO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
