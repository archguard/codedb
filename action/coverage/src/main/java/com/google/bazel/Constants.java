// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.bazel;

/**
 * Stores markers used by the lcov tracefile and gcov intermediate format file. See <a
 * href="http://ltp.sourceforge.net/coverage/lcov/geninfo.1.php">lcov documentation</a> and the flag
 * {@code --intermediate-format} in <a href="https://gcc.gnu.org/onlinedocs/gcc/Invoking-Gcov.html">
 * gcov documentation</a>.
 */
public class Constants {
  public static final String SF_MARKER = "SF:";
  public static final String FN_MARKER = "FN:";
  public static final String FNDA_MARKER = "FNDA:";
  public static final String FNF_MARKER = "FNF:";
  public static final String FNH_MARKER = "FNH:";
  public static final String BRDA_MARKER = "BRDA:";
  public static final String BA_MARKER = "BA:";
  public static final String BRF_MARKER = "BRF:";
  public static final String BRH_MARKER = "BRH:";
  public static final String DA_MARKER = "DA:";
  public static final String LH_MARKER = "LH:";
  public static final String LF_MARKER = "LF:";
  public static final String END_OF_RECORD_MARKER = "end_of_record";
  public static final String NEVER_EVALUATED = "-";
  public static final String TRACEFILE_EXTENSION = ".dat";
  public static final String GCOV_EXTENSION = ".gcov";
  public static final String GCOV_JSON_EXTENSION = ".gcov.json.gz";
  public static final String PROFDATA_EXTENSION = ".profdata";
  public static final String DELIMITER = ",";
  public static final String GCOV_VERSION_MARKER = "version:";
  public static final String GCOV_CWD_MARKER = "cwd:";
  public static final String GCOV_FILE_MARKER = "file:";
  public static final String GCOV_FUNCTION_MARKER = "function:";
  public static final String GCOV_LINE_MARKER = "lcount:";
  public static final String GCOV_BRANCH_MARKER = "branch:";
  public static final String GCOV_BRANCH_NOTEXEC = "notexec";
  public static final String GCOV_BRANCH_NOTTAKEN = "nottaken";
  public static final String GCOV_BRANCH_TAKEN = "taken";
}
