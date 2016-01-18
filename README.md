A SIMPLE RECYCLER VIEW
--------

Usag
------

```
dependencies {    
    compile 'com.xuie:recyclerview:0.0.2'
}    
```

```
ArrayAdapter arrayAdapter = new ArrayAdapter<String>(android.R.layout.simple_list_item_1, strings);
arrayAdapter.setAnimation(ArrayAdapter.ANI_BOTTOM_IN);
// arrayAdapter.setCustomAnimation(R.anim.item_custom);
// arrayAdapter.setOnItemClickListener(this);
recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
recycler.setAdapter(arrayAdapter);
```

License
-------
```
Copyright (C)  coolxuj Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
