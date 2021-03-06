package net.hockeyapp.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import net.hockeyapp.android.utils.Util;

/**
 * <h3>Description</h3>
 *
 * The expiry activity is shown when the build is expired.
 *
 * <h3>License</h3>
 *
 * <pre>
 * Copyright (c) 2011-2014 Bit Stadium GmbH
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * </pre>
 *
 * @author Thomas Dohmke
 **/
public class ExpiryInfoActivity extends Activity {
    /**
     * Called when the activity is starting. Sets the title and content view.
     * Configures the list view adapter. Attaches itself to a previously
     * started download task.
     *
     * @param savedInstanceState Data it most recently supplied in
     *                           onSaveInstanceState(Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.hockeyapp_expiry_info_title));
        setContentView(R.layout.activity_expiry_info);

        String appName = Util.getAppName(this);
        String text = String.format(getString(R.string.hockeyapp_expiry_info_text), appName);
        TextView messageView = (TextView) findViewById(R.id.label_message);
        messageView.setText(text);
    }

}
