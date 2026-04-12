package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.annotations.angular.NgDataType;
import com.jwebmp.core.base.angular.client.annotations.structures.NgSignal;
import com.jwebmp.core.base.angular.client.services.interfaces.INgDataType;

@NgDataType(NgDataType.DataTypeClass.Class)
@NgSignal(value = "true",type = "boolean",referenceName = "useGradle")
@NgImportReference(value = "Injectable", reference = "@angular/core")
public class App implements INgDataType<App> {
    @Override
    public String renderBeforeClass() {
        return "@Injectable({ providedIn: 'root' })\n";
    }
}
